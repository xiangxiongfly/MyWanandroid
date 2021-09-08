package com.core.wanandroid.system

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.wanandroid.bean.system.SystemDataItem
import com.core.wanandroid.databinding.FragmentSystemBinding
import com.core.wanandroid.system.adapter.SystemAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SystemFragment : BaseBindingFragment<FragmentSystemBinding>() {
    private val viewModel by viewModels<SystemViewModel>()
    private val systemList = ArrayList<SystemDataItem>()
    private lateinit var mAdapter: SystemAdapter

    companion object {
        fun newInstance(): SystemFragment {
            return SystemFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initObserve()
        refresh()
    }

    private fun initRv() {
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            mAdapter = SystemAdapter(mContext, systemList)
            adapter = mAdapter
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.systemListFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        systemList.clear()
                        systemList.addAll(it.response.data)
                        mAdapter.notifyDataSetChanged()
                    }
                    is DataResult.Completion -> {
                        binding.refreshLayout.apply {
                            if (isRefreshing) {
                                finishRefresh()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun refresh() {
        viewModel.getSystemList()
    }
}