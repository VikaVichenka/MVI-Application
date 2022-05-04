package com.vikayarska.mvi.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikayarska.data.model.AppUser
import com.vikayarska.domain.model.User
import com.vikayarska.mvi.R

class UserViewHolder(itemView: View, val onClick: (AppUser) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    var user: AppUser? = null
        private set
    private val name = itemView.findViewById<TextView>(R.id.tv_name_user_item)
    private val imageView = itemView.findViewById<ImageView>(R.id.iv_user_item)

    init {
        itemView.setOnClickListener {
            user?.let {
                onClick(it)
            }
        }
    }

    fun bindTo(item: AppUser) {
        user = item
        name.text = item.fullName()

        Glide.with(itemView.context)
            .load(item.imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_user_avatar)
            .into(imageView)
    }
}
