package com.core.wanandroid.bean.me

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.core.app.base.BaseBindingActivity
import com.core.app.manager.ConfigUtils
import com.core.app.utils.AppUtils
import com.core.app.utils.CacheUtils
import com.core.app.utils.toast
import com.core.wanandroid.databinding.ActivitySettingBinding
import kotlinx.coroutines.launch

class SettingActivity : BaseBindingActivity<ActivitySettingBinding>() {
    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        binding.tvVersionName.text = "当前版本 ${AppUtils.getVersionName()}"
        binding.tvClearCache.text = CacheUtils.getCacheSize()
        binding.tvClearCache.setOnClickListener {
            AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定清理缓存名？")
                .setNegativeButton("取消", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("清理", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        deleteCache()
                    }
                })
                .show()
        }
        binding.logout.isVisible = ConfigUtils.isLogin()
        binding.logout.setOnClickListener {
            ConfigUtils.clear()
            finish()
        }
    }

    private fun deleteCache() {
        lifecycleScope.launch {
            CacheUtils.clearAllCache(mContext)
            toast("清理完成")
        }
    }
}