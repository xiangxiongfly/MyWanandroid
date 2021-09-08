package com.core.wanandroid.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.core.app.base.BaseActivity
import com.core.wanandroid.MainActivity
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(1000L)
            MainActivity.actionStart(mContext)
            finish()
        }
    }
}