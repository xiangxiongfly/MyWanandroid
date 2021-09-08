package com.core.wanandroid.bean.me

import android.view.View
import com.core.app.base.BaseBindingFragment
import com.core.app.manager.ConfigUtils
import com.core.wanandroid.databinding.FragmentMeBinding
import com.core.wanandroid.login.LoginActivity

class MeFragment : BaseBindingFragment<FragmentMeBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            MeFragment()
    }

    override fun initView(view: View) {
        super.initView(view)
        binding.logo.setOnClickListener {
            LoginActivity.actionStart(mContext)
        }
        binding.setting.setOnClickListener {
            SettingActivity.actionStart(mContext)
        }
    }


    override fun onResume() {
        super.onResume()
        if (ConfigUtils.isLogin()) {
            val userInfo = ConfigUtils.get()
            binding.username.text = "${userInfo?.username}\nid:${userInfo?.id}"
        } else {
            binding.username.text = "请先登陆"
        }
    }

}