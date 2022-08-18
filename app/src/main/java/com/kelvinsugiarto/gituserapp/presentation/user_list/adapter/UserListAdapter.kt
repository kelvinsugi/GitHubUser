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
import com.bumptech.glide.request.RequestOptions
import com.kelvinsugiarto.gituserapp.R
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel


class UserListAdapter(private val onClick: (UsersListModel?) -> Unit)
    : ListAdapter<UsersListModel, RecyclerView.ViewHolder>(DiffCallback()) {

    private var mDataSet: List<UsersListModel> = arrayListOf()


    val TOP_TYPE = 0
     val BOTTOM_TYPE = 2
     val ITEM_TYPE = 1


    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
        var tvUserUrl = itemView.findViewById<TextView>(R.id.tvUserUrl)
        var ivUserImageProfile = itemView.findViewById<ImageView>(R.id.ivUserProfileImage)

        fun bind(item: UsersListModel,onClick: (UsersListModel?) -> Unit) = with(itemView) {
            tvUserName.text = item.login
            tvUserUrl.text = item.url
            val options = RequestOptions()

            Glide.with(itemView).load(item.avatar_url)
                .placeholder(R.drawable.rounded_placeholder)
                .apply(options.circleCrop())
                .into(ivUserImageProfile)

            setOnClickListener {
                onClick(item)
            }
        }
    }


    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return TOP_TYPE
        } else if (position == itemCount) {
            return BOTTOM_TYPE
        } else return ITEM_TYPE
//        return super.getItemViewType(position)
    }

    override fun submitList(list: List<UsersListModel>?) {
        if (list != null) {
            mDataSet = list
        }
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}