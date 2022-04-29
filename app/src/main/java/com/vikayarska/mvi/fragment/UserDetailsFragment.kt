package com.vikayarska.mvi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vikayarska.domain.intents.UserDetailsIntent
import com.vikayarska.domain.model.User
import com.vikayarska.domain.viewstates.UserDetailsScreenState
import com.vikayarska.mvi.R
import com.vikayarska.mvi.databinding.FragmentUserDetailsBinding
import com.vikayarska.mvi.viewmodel.UserDetailsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    val args: UserDetailsFragmentArgs by navArgs()

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    @EntryPoint
    @InstallIn(FragmentComponent::class)
    interface ViewModelFactoryProvider {
        fun userDetailsFactory(): UserDetailsViewModel.Factory
    }

    @Inject
    lateinit var viewModelAssistedFactory: UserDetailsViewModel.Factory

    private val viewModel: UserDetailsViewModel by viewModels {
        args.userId.let { userId ->
            UserDetailsViewModel.provideFactory(viewModelAssistedFactory, userId)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpViewState()
        binding.ivSaveUserDetails.setOnClickListener {
            viewModel.sendIntent(
                UserDetailsIntent.SaveUser(
                    User(
                        viewModel.userId,
                        binding.etNameUserDetails.text.toString(),
                        binding.etInfoUserItem.text.toString(),
                        viewModel.user?.imageUrl ?: ""
                    )
                )
            )
        }

        binding.ivCancelUserDetails.setOnClickListener {
            viewModel.sendIntent(UserDetailsIntent.FetchUser(viewModel.userId))
        }

        binding.ivEditUserDetails.setOnClickListener {
            viewModel.sendIntent(UserDetailsIntent.EditUser)
        }

        return view
    }

    private fun setUpViewState() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is UserDetailsScreenState.Empty -> {
                    showLoading(Visibility.Hide)
                    binding.ivEditUserDetails.visibility = View.GONE
                    binding.etNameUserDetails.setText(getString(R.string.no_user))
                    enableEditing(false)
                }
                is UserDetailsScreenState.Loading -> {
                    showLoading(Visibility.Show)
                }
                is UserDetailsScreenState.Editing -> {
                    showLoading(Visibility.Hide)
                    binding.ivEditUserDetails.visibility = View.GONE
                    enableEditing(true)
                }
                is UserDetailsScreenState.Preview -> {
                    showLoading(Visibility.Hide)
                    binding.ivEditUserDetails.visibility = View.VISIBLE
                    binding.etNameUserDetails.setText(state.user.name)
                    binding.etInfoUserItem.setText(state.user.intro)
                    val image = binding.ivUserItemDetails
                    Glide.with(image.context)
                        .load(state.user.imageUrl)
                        .circleCrop()
                        .placeholder(R.drawable.ic_user_avatar)
                        .into(image)
                    enableEditing(false)
                }
                is UserDetailsScreenState.Error -> {
                    showLoading(Visibility.Hide)
                    showError(message = state.message)
                }
            }
        })
    }

    private fun enableEditing(isEnable: Boolean) {
        binding.etNameUserDetails.isEnabled = isEnable
        binding.etInfoUserItem.isEnabled = isEnable

        if (isEnable) {
            binding.ivCancelUserDetails.visibility = View.VISIBLE
            binding.ivSaveUserDetails.visibility = View.VISIBLE
        } else {
            binding.ivCancelUserDetails.visibility = View.GONE
            binding.ivSaveUserDetails.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}