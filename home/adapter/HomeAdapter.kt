package com.core.wanandroid.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.core.app.ext.toHtml
import com.core.app.manager.ConfigUtils
import com.core.wanandroid.R
import com.core.wanandroid.bean.home.ArticleItem
import com.core.wanandroid.bean.home.BannerDataItem
import com.core.wanandroid.databinding.ItemHomeArticleBinding
import com.core.wanandroid.databinding.ItemHomeBannerBinding
import com.core.wanandroid.login.LoginActivity
import com.core.wanandroid.webview.WebViewActivity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener

class HomeAdapter(
    private val context: Context,
    private val owner: LifecycleOwner,
    private val list: ArrayList<ArticleItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bannerList = ArrayList<BannerDataItem>()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun addBanner(bannerList: ArrayList<BannerDataItem>) {
        this.bannerList = bannerList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BANNER else TYPE_ARTICLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BANNER -> {
                BannerViewHolder(
                    ItemHomeBannerBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            else -> {
                ArticleViewHolder(
                    ItemHomeArticleBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
        }
    }

    fun getRealPosition(position: Int): Int {
        if (bannerList.size > 0) {
            return position - 1
        } else {
            return position
        }
    }

    fun setItemData(position: Int, item: ArticleItem) {
        if (bannerList.size > 0) {
            val adapterPosition = position + 1
            list[adapterPosition] = item
            notifyItemChanged(adapterPosition)
        } else {
            list[position] = item
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER) {
            val viewHolder = viewHolder as BannerViewHolder
            viewHolder.binding.banner.setAdapter(object :
                BannerImageAdapter<BannerDataItem>(bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder,
                    data: BannerDataItem,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(holder.itemView)
                        .load(data.imagePath)
                        .into(holder.imageView)
                }
            })
                .setOnBannerListener(object : OnBannerListener<BannerDataItem> {
                    override fun OnBannerClick(data: BannerDataItem, position: Int) {
                        WebViewActivity.actionStart(context, data.url, data.title)
                    }
                })
                .addBannerLifecycleObserver(owner) //添加生命周期观察者
                .setIndicator(CircleIndicator(context))
        } else {
            val viewHolder = viewHolder as ArticleViewHolder
            val articleItem = list[getRealPosition(position)]
            viewHolder.binding.tvArticleTitile.text = articleItem.title.toHtml()
            viewHolder.binding.tvArticleTag.text = articleItem.superChapterName
            viewHolder.binding.tvArticleTime.text = articleItem.niceDate
            viewHolder.binding.tvArticleAuthor.text = articleItem.shareUser

            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_favorite)
            drawable?.let {
                DrawableCompat.setTint(it, if (articleItem.collect) Color.RED else Color.GRAY)
                viewHolder.binding.ivFavorite.setImageDrawable(drawable)
            }

            viewHolder.binding.ivFavorite.setOnClickListener {
                if (!ConfigUtils.isLogin()) {
                    LoginActivity.actionStart(context)
                    return@setOnClickListener
                }
                action?.invoke(articleItem.collect, articleItem.id, getRealPosition(position))
            }

            viewHolder.itemView.setOnClickListener {
                WebViewActivity.actionStart(context, articleItem.link, articleItem.title)
            }
        }
    }

    override fun getItemCount(): Int = if (bannerList.size > 0) list.size + 1 else list.size

    inner class ArticleViewHolder(val binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class BannerViewHolder(val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val TYPE_BANNER = 0
        const val TYPE_ARTICLE = 1
    }

    private var action: ((Boolean, Int, Int) -> Unit)? = null

    fun setCollectAction(action: (Boolean, Int, Int) -> Unit) {
        this.action = action
    }

}