package com.core.wanandroid.navigation

import androidx.lifecycle.viewModelScope
import com.core.app.entity.BaseResponse
import com.core.app.base.BaseViewModel
import com.core.app.DataResult
import com.core.wanandroid.bean.navigation.NavigationDataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NavigationViewModel : BaseViewModel() {

    private val repo = NavigationRepo()

    private val _getNavigationListFlow =
        MutableStateFlow<DataResult<BaseResponse<ArrayList<NavigationDataItem>>>>(DataResult.None)
    val getNavigationListFlow = _getNavigationListFlow.asStateFlow()

    fun getNavigationList() {
        viewModelScope.launch {
            repo.getNavigationList().collect {
                _getNavigationListFlow.value = it
            }
        }
    }


}