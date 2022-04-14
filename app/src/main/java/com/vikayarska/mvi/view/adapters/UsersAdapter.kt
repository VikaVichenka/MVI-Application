package com.vikayarska.mvi.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikayarska.domain.model.User
import com.vikayarska.mvi.R
import com.vikayarska.mvi.view.viewholder.UserViewHolder

class UsersAdapter(
    private val users: ArrayList<User>,
    private val onClick: (User) -> Unit
) :
    RecyclerView.Adapter<UserViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_user_item, viewGroup, false)

        return UserViewHolder(view, onClick)
    }


    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        viewHolder.bindTo(users[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = users.size

    fun getItems(): List<User> = users

    fun addData(list: List<User>) {
        users.addAll(list)
    }


    fun replaceData(list: List<User>) {
        users.clear()
        users.addAll(list)
    }
}
