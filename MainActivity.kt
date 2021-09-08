package com.core.wanandroid

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.core.app.base.BaseBindingActivity
import com.core.app.network.NetworkStateManager
import com.core.wanandroid.bean.me.MeFragment
import com.core.wanandroid.databinding.ActivityMainBinding
import com.core.wanandroid.home.HomeFragment
import com.core.wanandroid.navigation.NavigationFragment
import com.core.wanandroid.project.ProjectFragment
import com.core.wanandroid.system.SystemFragment

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    private val tags = arrayOf(
        HomeFragment.javaClass.name.toString(),
        SystemFragment.javaClass.name.toString(),
        NavigationFragment.javaClass.name.toString(),
        ProjectFragment.javaClass.name.toString(),
        MeFragment.javaClass.name.toString()
    )
    private val fragments = arrayOfNulls<Fragment>(tags.size)
    private var showFragment: Fragment? = null
    private var currentIndex: Int = 0

    companion object {
        const val CURRENT_INDEX = "current_index"

        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    switchFragment(0)
                }
                R.id.system -> {
                    switchFragment(1)
                }
                R.id.navigation -> {
                    switchFragment(2)
                }
                R.id.project -> {
                    switchFragment(3)
                }
                R.id.me -> {
                    switchFragment(4)
                }
            }
            true
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        if (savedInstanceState != null) {
            fragments.forEachIndexed { index, _ ->
                val fragment: Fragment? =
                    supportFragmentManager.getFragment(savedInstanceState, tags[index])
                if (fragment != null) {
                    fragments[index] = fragment
                }
            }
            currentIndex = savedInstanceState.getInt(CURRENT_INDEX, 0)
        }
        switchFragment(currentIndex)



        Handler()
//        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun click(v: View) {
        viewModel.sendMessage()
    }

    private fun getFragmentByIndex(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment.newInstance()
            1 -> SystemFragment.newInstance()
            2 -> ProjectFragment.newInstance()
            3 -> NavigationFragment.newInstance()
            4 -> MeFragment.newInstance()
            else -> {
                throw IllegalArgumentException("非法参数")
            }
        }
    }

    private fun switchFragment(index: Int) {
        if (fragments[index] == null) {
            fragments[index] = getFragmentByIndex(index)
        }
        val to: Fragment? = fragments[index]

        if (to == null || showFragment == to)
            return

        currentIndex = index
        supportFragmentManager.beginTransaction().apply {
            if (!to.isAdded) {
                showFragment?.let { hide(it) }
                add(R.id.fragment_container, to, tags[index])
            } else {
                showFragment?.let { hide(it) }
                show(to)
            }
            showFragment = to
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        fragments.forEachIndexed { index, fragment ->
            if (fragment != null && fragment.isAdded) {
                supportFragmentManager.putFragment(outState, tags[index], fragment)
            }
        }
        outState.putInt(CURRENT_INDEX, currentIndex)
        super.onSaveInstanceState(outState)
    }

    private fun getNetStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        val netDataResponse = 0//接收
        val netDataSend = 0//发送
        val manage = getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStateManager

    }
}
