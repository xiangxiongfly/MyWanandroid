package com.core.wanandroid.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.app.DataResult
import com.core.app.base.BaseBindingActivity
import com.core.app.manager.ConfigUtils
import com.core.app.utils.toast
import com.core.wanandroid.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<LoginViewModel>()

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        initObserve()
        binding.btnLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            if (checkParams(username, password)) {
                viewModel.login(username, password)
            }
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.loginFlow.collect {
                when (it) {
                    is DataResult.Start -> {
                        showLoading()
                    }
                    is DataResult.Success -> {
                        val data = it.response.data
                        ConfigUtils.save(data.username, data.id)
                        ConfigUtils.setLoginState(true)
                        finish()
                    }
                    is DataResult.Error -> {
                        toast(it.exception.msg)
                    }
                    is DataResult.Completion -> {
                        dismissLoading()
                    }
                }
            }
        }
    }

    private fun checkParams(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) {
            toast("请输入用户名和密码")
            return false
        }
        return true
    }
}