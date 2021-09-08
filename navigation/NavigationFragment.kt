package com.core.wanandroid.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.wanandroid.bean.navigation.Article
import com.core.wanandroid.bean.navigation.NavigationDataItem
import com.core.wanandroid.databinding.FblItemBinding
import com.core.wanandroid.databinding.FragmentNavigationBinding
import com.core.wanandroid.navigation.adapter.NavigationAdapter
import com.core.wanandroid.webview.WebViewActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NavigationFragment : BaseBindingFragment<FragmentNavigationBinding>() {
    private val viewModel by viewModels<NavigationViewModel>()

    private val mList = ArrayList<NavigationDataItem>()
    private lateinit var mAdapter: NavigationAdapter

    companion object {
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        initRv()
    }

    private fun initRv() {
        binding.refreshLayout.setOnRefreshListener { loadData() }
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
            mAdapter = NavigationAdapter(mContext, mList)
            mAdapter.setOnItemClick {
                handleFlexboxLayout(mList[it].articles)
            }
            adapter = mAdapter
        }
    }

    private fun handleFlexboxLayout(articles: List<Article>) {
        binding.fblContent.removeAllViews()
        val layoutInflater = LayoutInflater.from(mContext)
        articles.forEach { item ->
            val bind = FblItemBinding.inflate(layoutInflater, binding.fblContent, false)
            bind.childName.text = item.title
            bind.root.setOnClickListener {
                WebViewActivity.actionStart(mContext, item.link)
            }
            binding.fblContent.addView(bind.root)
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initObserve()
        loadData()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.getNavigationListFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        mList.clear()
                        mList.addAll(it.response.data)
                        mAdapter.notifyDataSetChanged()

                        if (mList.size > 0)
                            handleFlexboxLayout(mList[0].articles)
                    }
                    is DataResult.Completion -> {
                        binding.refreshLayout.apply {
                            if (isRefreshing) {
                                finishRefresh()
                            }
                        }
                    }
                    else -> {

                    }
                }

            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getNavigationList()
        }
    }
}