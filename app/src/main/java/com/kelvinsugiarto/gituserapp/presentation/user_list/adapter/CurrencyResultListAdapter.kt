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
import com.kelvinsugiarto.gituserapp.data.model.CurrencyRateModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel


class CurrencyResultListAdapter(private val onClick: (CurrencyRateModel?) -> Unit)
    : ListAdapter<CurrencyRateModel, CurrencyResultListAdapter.ItemViewholder>(DiffCallback()) {


    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCurrencyName = itemView.findViewById<TextView>(R.id.tvCurrencyListName)
        var tvCurrencyConvertResult = itemView.findViewById<TextView>(R.id.tvConversionResult)
//        var ivUserImageProfile = itemView.findViewById<ImageView>(R.id.ivUserProfileImage)

        fun bind(item: CurrencyRateModel,onClick: (CurrencyRateModel?) -> Unit) = with(itemView) {
            tvCurrencyName.text = item.currencyName
            tvCurrencyConvertResult.text = item.currencyResult.toString()

            val options = RequestOptions()

//            Glide.with(itemView).load(item.avatar_url)
//                .placeholder(R.drawable.ic_avatar_1577909)
//                .apply(options.circleCrop())
//                .into(ivUserImageProfile)

            setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun submitList(list: List<CurrencyRateModel>?) {
        super.submitList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position),onClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<CurrencyRateModel>() {
        override fun areItemsTheSame(oldItem: CurrencyRateModel, newItem: CurrencyRateModel): Boolean {
            return oldItem?.currencyCode == newItem?.currencyCode
        }

        override fun areContentsTheSame(oldItem: CurrencyRateModel, newItem: CurrencyRateModel): Boolean {
            return oldItem == newItem
        }
    }
}