package com.core.wanandroid.project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.core.app.DataResult
import com.core.app.base.BaseBindingFragment
import com.core.app.ext.toHtml
import com.core.wanandroid.databinding.FragmentProjectBinding
import com.core.wanandroid.project.child.ProjectChildFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProjectFragment : BaseBindingFragment<FragmentProjectBinding>() {
    private val viewModel by viewModels<ProjectViewModel>()

    private val titleList = ArrayList<String>()
    private val fragmentList = ArrayList<ProjectChildFragment>()

    companion object {
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        viewModel.getProjectTitle()
    }

    private fun initViewPager() {
        //初始化ViewPager2
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = titleList[position].toHtml()
                }
            }).attach()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.projectTitleFlow.collect {
                when (it) {
                    is DataResult.Success -> {
                        titleList.add("最新项目")
                        fragmentList.add(ProjectChildFragment.newInstance(0))

                        it.response.data.forEach {
                            titleList.add(it.name)
                            fragmentList.add(ProjectChildFragment.newInstance(it.id))
                        }
                        initViewPager()
                    }
                }
            }
        }

    }

}