package com.core.wanandroid.api

import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.system.SystemChildData
import com.core.wanandroid.bean.system.SystemDataItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemApi {

    @GET("tree/json")
    suspend fun getSystemList(): BaseResponse<ArrayList<SystemDataItem>>

    @GET("article/list/{page}/json")
    suspend fun getSystemChildArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<SystemChildData>
}