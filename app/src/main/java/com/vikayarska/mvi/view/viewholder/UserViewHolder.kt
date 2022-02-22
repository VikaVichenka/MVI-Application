package com.vikayarska.mvi.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vikayarska.domain.model.User
import com.vikayarska.mvi.R

class UserViewHolder(itemView: View, val onClick: (User) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    var user: User? = null
        private set
    private val name = itemView.findViewById<TextView>(R.id.tv_name_user_item)

    init {
        itemView.setOnClickListener {
            user?.let {
                onClick(it)
            }
        }
    }

    fun bindTo(item: User) {
        user = item
        name.text = item.name
    }
}
