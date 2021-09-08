package com.core.wanandroid.navigation

import com.core.app.base.BaseRepository
import com.core.app.entity.BaseResponse
import com.core.app.DataResult
import com.core.app.network.HttpManager
import com.core.wanandroid.api.NavigationApi
import com.core.wanandroid.bean.navigation.NavigationDataItem
import kotlinx.coroutines.flow.Flow

class NavigationRepo : BaseRepository() {
    private val api = HttpManager.create(NavigationApi::class.java)

    suspend fun getNavigationList(): Flow<DataResult<BaseResponse<ArrayList<NavigationDataItem>>>> {
        return executeRepo { api.getNavigation() }
    }
}