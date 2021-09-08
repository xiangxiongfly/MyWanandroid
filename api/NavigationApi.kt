package com.core.wanandroid.api

import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.navigation.NavigationDataItem
import retrofit2.http.GET

interface NavigationApi {

    @GET("navi/json")
    suspend fun getNavigation(): BaseResponse<ArrayList<NavigationDataItem>>
}