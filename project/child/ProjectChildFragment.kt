package com.core.wanandroid.project.child

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.wanandroid.bean.project.ProjectChildDataItem
import com.core.wanandroid.databinding.FragmentProjectChildBinding
import com.core.wanandroid.project.ProjectViewModel
import com.core.wanandroid.project.adapter.ProjectAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectChildFragment : BaseBindingFragment<FragmentProjectChildBinding>() {
    private val viewModel by viewModels<ProjectViewModel>()

    private var cid = 0
    private var projectList = ArrayList<ProjectChildDataItem>()
    private lateinit var mAdapter: ProjectAdapter

    companion object {
        fun newInstance(id: Int): ProjectChildFragment {
            val fragment = ProjectChildFragment()
            val args = Bundle()
            args.putInt("cid", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getInt("cid")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated $cid")
        initRv(view)
        initObserve()
        refresh()
    }

    private fun initRv(view: View) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
            mAdapter = ProjectAdapter(mContext, projectList)
            adapter = mAdapter
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
            viewModel.projectChildFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        projectList.clear()
                        projectList.addAll(it.response.data.datas)
                        mAdapter.notifyDataSetChanged()
                    }
                    is DataResult.Completion -> {
                        binding.refreshLayout.apply {
                            if (isRefreshing) {
                                finishRefresh()
                            }
                            if (isLoading) {
                                finishLoadMore()
                            }
                        }
                    }

                }
            }
        }
    }

    private fun refresh() {
        viewModel.getProjectChildList(cid, true)
    }

    private fun loadMore() {
        viewModel.getProjectChildList(cid, false)
    }

}