package com.core.wanandroid.system.child

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.app.utils.log
import com.core.app.utils.toast
import com.core.wanandroid.bean.system.SystemChildArticleDataItem
import com.core.wanandroid.databinding.FragmentSystemChildListBinding
import com.core.wanandroid.system.SystemViewModel
import com.core.wanandroid.system.adapter.SystemArticleAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SystemChildListFragment : BaseBindingFragment<FragmentSystemChildListBinding>() {
    private val viewModel by viewModels<SystemViewModel>()
    private lateinit var mAdapter: SystemArticleAdapter
    private var articleId: Int = 0

    private val mList = ArrayList<SystemChildArticleDataItem>()

    companion object {
        @JvmStatic
        fun newInstance(articleId: Int): SystemChildListFragment {
            return SystemChildListFragment().apply {
                arguments = Bundle().apply {
                    putInt("articleId", articleId)
                }
            }
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(mContext)
            mAdapter = SystemArticleAdapter(mContext, mList)
            adapter = mAdapter
        }

        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        arguments?.apply {
            articleId = getInt("articleId")
            log("articleId $articleId")
        }

        initObserve()
        refresh()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.systemChildListFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        if (it.response.isRefresh) {
                            mList.clear()
                            mList.addAll(it.response.data.datas)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            mList.addAll(it.response.data.datas)
                            mAdapter.notifyDataSetChanged()
                        }
                    }
                    is DataResult.Error -> {
                        toast("加载失败")
                    }
                    is DataResult.Completion -> {
                        finishRefresh(it.isRefresh)
                    }
                }
            }
        }
    }

    private fun finishRefresh(isRefresh: Boolean) {
        binding.refreshLayout.apply {
            if (isRefresh) {
                if (isRefreshing) finishRefresh()
            } else {
                if (isLoading) finishLoadMore()
            }
        }
    }

    private fun refresh() {
        viewModel.getSystemChildArticleList(articleId, true)
    }

    private fun loadMore() {
        viewModel.getSystemChildArticleList(articleId, false)
    }

}