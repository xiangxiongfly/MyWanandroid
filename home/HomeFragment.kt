package com.core.wanandroid.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.app.utils.toast
import com.core.wanandroid.bean.home.ArticleItem
import com.core.wanandroid.bean.home.BannerDataItem
import com.core.wanandroid.databinding.FragmentHomeBinding
import com.core.wanandroid.home.adapter.HomeAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModels<HomeViewModel>()

    private val articleList = ArrayList<ArticleItem>()
    private lateinit var homeAdapter: HomeAdapter

    private val bannerList = ArrayList<BannerDataItem>()

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initObserve()

        refresh()
    }

    private fun initRv() {
        homeAdapter = HomeAdapter(mContext, this, articleList).apply {
            setCollectAction { collect, id, position ->
                if (collect) {
                    requestUnCollect(id, position)
                } else {
                    requestCollect(id, position)
                }
            }
        }
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
            adapter = homeAdapter
        }

        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            loadMore()
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            homeViewModel.articleFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        if (it.response.isRefresh) {
                            articleList.clear()
                            articleList.addAll(it.response.data.datas)
                        } else {
                            articleList.addAll(it.response.data.datas)
                        }
                        homeAdapter.notifyDataSetChanged()
                        finishRefresh(it.response.isRefresh)
                    }
                    is DataResult.Error -> {
                        toast("列表加载失败")
                        finishRefresh(it.exception.isRefresh)
                    }
                }
            }
        }

        lifecycleScope.launch {
            homeViewModel.bannerFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        bannerList.clear()
                        bannerList.addAll(it.response.data)
                        homeAdapter.addBanner(bannerList)
                    }
                    is DataResult.Error -> {
                        toast("轮播图加载失败")
                    }
                }
            }
        }

        lifecycleScope.launch {
//            homeViewModel.collectFlow.collect {
//                it?.apply {
//                    if (isSuccess) {
//                        articleList[position].collect = collect
//                        homeArticleAdapter.notifyItemChanged(position)
//                        toast(errMsg)
//                    } else {
//                        toast(errMsg)
//                    }
//                }
//            }

            homeViewModel.collectLiveData.observe(viewLifecycleOwner) {
                it?.apply {
                    if (isSuccess) {
                        articleList[position].collect = collect
                        homeAdapter.setItemData(position, articleList[position])
                        toast(errMsg)
                    } else {
                        toast(errMsg)
                    }
                }
            }
        }
    }

    private fun finishRefresh(isRefresh: Boolean) {
        if (isRefresh) {
            binding.refreshLayout.apply {
                if (isRefreshing) {
                    finishRefresh(1000)
                }
            }
        } else {
            binding.refreshLayout.apply {
                if (isLoading) {
                    finishLoadMore(1000)
                }
            }
        }
    }

    private fun refresh() {
        homeViewModel.getArticle(true)
        homeViewModel.getBanner()
    }

    private fun loadMore() {
        homeViewModel.getArticle(false)
    }

    private fun requestCollect(id: Int, position: Int) {
        homeViewModel.collect(id, position)
    }

    private fun requestUnCollect(id: Int, position: Int) {
        homeViewModel.unCollect(id, position)
    }
}