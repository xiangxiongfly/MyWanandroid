package com.core.wanandroid.project

import com.core.app.base.BaseRepository
import com.core.app.entity.BaseResponse
import com.core.app.DataResult
import com.core.app.network.HttpManager
import com.core.wanandroid.api.ProjectApi
import com.core.wanandroid.bean.project.ProjectChildData
import com.core.wanandroid.bean.project.ProjectTitleDataItem
import kotlinx.coroutines.flow.Flow

class ProjectRepo : BaseRepository() {
    private val api = HttpManager.create(ProjectApi::class.java)

    suspend fun getProjectTitle(): Flow<DataResult<BaseResponse<ArrayList<ProjectTitleDataItem>>>> {
        return executeRepo { api.getProjectTitle() }
    }

    suspend fun getProjectChildList(
        cid: Int,
        page: Int
    ): Flow<DataResult<BaseResponse<ProjectChildData>>> {
        return executeRepo { api.getProjectChildList(page, cid) }
    }

}