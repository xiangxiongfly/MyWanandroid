package com.core.wanandroid.api

import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.home.ArticleData
import com.core.wanandroid.bean.home.BannerDataItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeApi {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseResponse<ArticleData>

    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<ArrayList<BannerDataItem>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResponse<Any>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): BaseResponse<Any>
}