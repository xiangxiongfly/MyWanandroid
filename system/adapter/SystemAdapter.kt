package com.core.wanandroid.system.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.wanandroid.bean.system.SystemDataItem
import com.core.wanandroid.databinding.FblItemBinding
import com.core.wanandroid.databinding.ItemSystemBinding
import com.core.wanandroid.system.child.SystemChildActivity

class SystemAdapter(val context: Context, val list: ArrayList<SystemDataItem>) :
    RecyclerView.Adapter<SystemAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(val binding: ItemSystemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSystemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            name.text = item.name
            fbl.removeAllViews()
            item.children.forEachIndexed { index, it ->
                val itemBinding = FblItemBinding.inflate(layoutInflater, fbl, false)
                itemBinding.childName.text = it.name
                itemBinding.root.setOnClickListener {
                    SystemChildActivity.actionStart(context, index, item.children)
                }
                fbl.addView(itemBinding.root)
            }
        }
        holder.binding.root.setOnClickListener {
            SystemChildActivity.actionStart(context, 0, item.children)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}