package com.core.wanandroid.system

import androidx.lifecycle.viewModelScope
import com.core.app.entity.BaseResponse
import com.core.app.base.BaseViewModel
import com.core.app.DataResult
import com.core.wanandroid.bean.system.SystemChildData
import com.core.wanandroid.bean.system.SystemDataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SystemViewModel : BaseViewModel() {
    private val repo = SystemRepo()

    private val _systemListFlow =
        MutableStateFlow<DataResult<BaseResponse<ArrayList<SystemDataItem>>>>(DataResult.None)
    val systemListFlow = _systemListFlow.asStateFlow()

    private val _systemChildListFlow =
        MutableStateFlow<DataResult<BaseResponse<SystemChildData>>>(DataResult.None)
    val systemChildListFlow = _systemChildListFlow.asStateFlow()

    fun getSystemList() {
        viewModelScope.launch {
            repo.getSystemList().collect {
                _systemListFlow.value = it
            }
        }
    }

    private var page: Int = 0

    fun getSystemChildArticleList(cid: Int, isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh)
                page = 0

            repo.getSystemChildArticleList(page, cid).collect {
                when (it) {
                    is DataResult.Success -> {
                        page++
                        it.response.isRefresh = isRefresh
                        _systemChildListFlow.value = it
                    }
                    is DataResult.Error -> {
                        it.exception.isRefresh = isRefresh
                        _systemChildListFlow.value = it
                    }
                    is DataResult.Completion -> {
                        it.isRefresh = isRefresh
                        _systemChildListFlow.value = it
                    }
                    else -> {
                        _systemChildListFlow.value = it
                    }
                }
            }
        }
    }

}