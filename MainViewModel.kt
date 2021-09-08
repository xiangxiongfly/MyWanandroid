package com.core.wanandroid

import com.core.app.base.BaseViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class MainViewModel : BaseViewModel() {
    val liveData = SingleLiveData<String>()
    val liveData2 = liveData

    val unPeekLiveData = UnPeekLiveData.Builder<String>()
        .setAllowNullValue(false)
        .create()
    val unPeekLiveData2 = unPeekLiveData


    fun sendMessage() {
        liveData.value = "aaaaaa"
        unPeekLiveData.value = "hello"
    }


}