package com.core.wanandroid.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.app.utils.ImageLoader
import com.core.wanandroid.bean.project.ProjectChildDataItem
import com.core.wanandroid.databinding.ItemProjectChildBinding
import com.core.wanandroid.webview.WebViewActivity

class ProjectAdapter(val context: Context, var list: ArrayList<ProjectChildDataItem>) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(val binding: ItemProjectChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProjectChildBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.author.text = item.author
        holder.binding.title.text = item.title
        holder.binding.desc.text = item.desc
        holder.binding.time.text = item.niceDate
        holder.binding.type.text = "${item.superChapterName}-${item.chapterName}"
        ImageLoader.load(holder.itemView, holder.binding.pic, item.envelopePic)

        holder.itemView.setOnClickListener {
            WebViewActivity.actionStart(context, item.link, item.title)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}