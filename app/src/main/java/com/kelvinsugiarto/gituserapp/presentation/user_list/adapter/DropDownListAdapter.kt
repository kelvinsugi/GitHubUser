package com.kelvinsugiarto.gituserapp.presentation.user_list.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kelvinsugiarto.gituserapp.R

class DropDownListAdapter(private val mContext: Context,
                          private val mLayoutResourceId: Int,
                          cities: List<String>) : ArrayAdapter<String>(mContext,mLayoutResourceId,cities) {
    private val city: MutableList<String> = ArrayList(cities)
    private var allCities: List<String> = ArrayList(cities)

    override fun getCount(): Int {
        return city.size
    }
    override fun getItem(position: Int): String {
        return city[position]
    }

    fun clearData(){
        city.clear()
        notifyDataSetChanged()
    }

    fun updateData(currencyList:MutableList<String>){
        city.addAll(currencyList)
        notifyDataSetChanged()
    }
//    override fun getItemId(position: Int): Long {
//        return city[position].id.toLong()
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val city: String = getItem(position)
            val cityAutoCompleteView = convertView!!.findViewById<View>(R.id.tvCurrencyName) as TextView
            cityAutoCompleteView.text = city
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }



}