package com.core.wanandroid.navigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.wanandroid.bean.navigation.NavigationDataItem
import com.core.wanandroid.databinding.ItemNavigationMenuBinding

class NavigationAdapter(val context: Context, val list: ArrayList<NavigationDataItem>) :
    RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(val binding: ItemNavigationMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNavigationMenuBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menuName.text = list[position].name
        holder.itemView.setOnClickListener {
            actlinClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = list.size


    private var actlinClick: ((Int) -> Unit)? = null

    fun setOnItemClick(callback: (Int) -> Unit) {
        actlinClick = callback
    }

}