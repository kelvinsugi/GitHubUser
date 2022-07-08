package com.kelvinsugiarto.gituserapp.presentation.user_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelvinsugiarto.gituserapp.R
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel

class UserListAdapter(private val onClick: (UsersListModel?) -> Unit)
    : ListAdapter<UsersListModel, UserListAdapter.ItemViewholder>(DiffCallback()) {


    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
        var tvUserUrl = itemView.findViewById<TextView>(R.id.tvUserUrl)
        var ivUserImageProfile = itemView.findViewById<ImageView>(R.id.ivUserProfileImage)

        fun bind(item: UsersListModel,onClick: (UsersListModel?) -> Unit) = with(itemView) {
            tvUserName.text = item.login
            tvUserUrl.text = item.url

            Glide.with(itemView).load(item.avatar_url).into(ivUserImageProfile)

            setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun submitList(list: List<UsersListModel>?) {
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position),onClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<UsersListModel>() {
        override fun areItemsTheSame(oldItem: UsersListModel, newItem: UsersListModel): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: UsersListModel, newItem: UsersListModel): Boolean {
            return oldItem == newItem
        }
    }
}