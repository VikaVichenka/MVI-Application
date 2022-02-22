package com.vikayarska.mvi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vikayarska.domain.intents.UserListIntent
import com.vikayarska.domain.viewstates.BaseScreenState
import com.vikayarska.mvi.databinding.FragmentUsersListBinding
import com.vikayarska.mvi.view.adapters.UsersAdapter
import com.vikayarska.mvi.viewmodel.UsersListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {
    private val viewModel: UsersListViewModel by viewModels()

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpViewState()

        userAdapter = UsersAdapter(arrayListOf(), onClick = {
            findNavController().navigate(
                UserListFragmentDirections.actionUsersListFragmentToUserDetailsFragment(it.id)
            )
        })
        binding.rvUsersList.apply {
            adapter = userAdapter
        }

        binding.btAddUsersList.setOnClickListener {
            viewModel.userIntent.value = UserListIntent.AddUsers
        }

        binding.btDeleteUsersList.setOnClickListener {
            viewModel.userIntent.value = UserListIntent.DeleteUsers
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpViewState() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseScreenState.Loaded -> {
                    showEmptyView(false)
                    showLoading(Visibility.Hide)
                    userAdapter.replaceData(state.data)
                    userAdapter.notifyDataSetChanged()
                }
                is BaseScreenState.Error -> {
                    showLoading(Visibility.Hide)
                    showError(state.message) {
                        viewModel.userIntent.value = UserListIntent.FetchUsers
                    }
                }
                is BaseScreenState.Loading -> showLoading(Visibility.Show)
                is BaseScreenState.Empty -> {
                    showLoading(Visibility.Hide)
                    showEmptyView(true)
                }
            }
        })
    }

    private fun showEmptyView(toShow: Boolean) {
        if (toShow) {
            binding.tvEmptyUsersList.visibility = View.VISIBLE
            binding.rvUsersList.visibility = View.GONE
        } else {
            binding.tvEmptyUsersList.visibility = View.GONE
            binding.rvUsersList.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.userIntent.value = UserListIntent.FetchUsers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}