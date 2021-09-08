package com.core.wanandroid.api

import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.project.ProjectChildData
import com.core.wanandroid.bean.project.ProjectTitleDataItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    @GET("project/tree/json")
    suspend fun getProjectTitle(): BaseResponse<ArrayList<ProjectTitleDataItem>>

    @GET("project/list/{page}/json")
    suspend fun getProjectChildList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectChildData>

}