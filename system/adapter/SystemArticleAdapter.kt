package com.core.wanandroid.system.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.wanandroid.bean.system.SystemChildArticleDataItem
import com.core.wanandroid.databinding.ItemSystemArticleBinding
import com.core.wanandroid.webview.WebViewActivity

class SystemArticleAdapter(val context: Context, val list: ArrayList<SystemChildArticleDataItem>) :
    RecyclerView.Adapter<SystemArticleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(val binding: ItemSystemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSystemArticleBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.author.text = item.author
        holder.binding.articleTime.text = item.niceDate
        holder.binding.articleContent.text = item.title
        holder.binding.articleType.text = "${item.superChapterName} - ${item.chapterName}"

        holder.itemView.setOnClickListener {
            WebViewActivity.actionStart(context, item.link, item.title)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}