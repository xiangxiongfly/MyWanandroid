package com.core.wanandroid.system.child

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.core.app.base.BaseBindingActivity
import com.core.wanandroid.bean.system.Children
import com.core.wanandroid.databinding.ActivitySystemChildBinding
import com.core.wanandroid.system.SystemViewModel
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class SystemChildActivity : BaseBindingActivity<ActivitySystemChildBinding>() {
    private val viewModel by viewModels<SystemViewModel>()

    private val titleList = ArrayList<String>()
    private val fragmentList = ArrayList<SystemChildListFragment>()

    companion object {
        fun actionStart(context: Context, index: Int, list: ArrayList<Children>) {
            context.startActivity(Intent(context, SystemChildActivity::class.java).apply {
                putExtra("index", index)
                putExtra("list", list)
            })
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val index = intent.getIntExtra("index", 0)
        val list = intent.getSerializableExtra("list") as ArrayList<Children>
        list.forEach {
            titleList.add(it.name)
            fragmentList.add(SystemChildListFragment.newInstance(it.id))
        }
        initIndicatorVp(index)
    }

    private fun initIndicatorVp(index: Int) {
        val navigator = CommonNavigator(this)
        navigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.setText(titleList[index])
                colorTransitionPagerTitleView.setOnClickListener {
                    binding.viewPager.setCurrentItem(index)
                }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        binding.indicator.navigator = navigator


        val mAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
        binding.viewPager.adapter = mAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.indicator.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                binding.indicator.onPageScrollStateChanged(state)
            }
        })

        binding.viewPager.currentItem = index
    }

}