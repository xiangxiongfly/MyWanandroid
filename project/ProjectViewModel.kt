package com.core.wanandroid.project

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.core.app.entity.BaseResponse
import com.core.app.base.BaseViewModel
import com.core.app.DataResult
import com.core.wanandroid.bean.project.ProjectChildData
import com.core.wanandroid.bean.project.ProjectTitleDataItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectViewModel : BaseViewModel() {
    private val repo = ProjectRepo()

    private val _projectTitleFlow =
        MutableStateFlow<DataResult<BaseResponse<ArrayList<ProjectTitleDataItem>>>>(DataResult.None)
    val projectTitleFlow = _projectTitleFlow.asStateFlow()

    private val _projectChildFlow =
        MutableStateFlow<DataResult<BaseResponse<ProjectChildData>>>(DataResult.None)
    val projectChildFlow = _projectChildFlow.asStateFlow()


    private var page = 0

    fun getProjectTitle() {
        viewModelScope.launch {
            repo.getProjectTitle()
                .collect {
                    _projectTitleFlow.value = it
                }
        }
    }

    fun getProjectChildList(cid: Int, isRefresh: Boolean) {
        MainScope().launch {
            page = 0
            repo.getProjectChildList(cid, page).collect {
                Log.e("TAG", "----------->  $it")
                when (it) {
                    is DataResult.Success -> {
                        page++
                        it.response.isRefresh = isRefresh
                        _projectChildFlow.value = it
                    }
                    is DataResult.Error -> {
                        it.exception.isRefresh = isRefresh
                        _projectChildFlow.value = it
                    }
                    else -> {
                        _projectChildFlow.value = it
                    }
                }
            }
        }
    }
}