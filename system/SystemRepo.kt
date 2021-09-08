package com.core.wanandroid.system

import com.core.app.base.BaseRepository
import com.core.app.entity.BaseResponse
import com.core.app.DataResult
import com.core.app.network.HttpManager
import com.core.wanandroid.api.SystemApi
import com.core.wanandroid.bean.system.SystemChildData
import com.core.wanandroid.bean.system.SystemDataItem
import kotlinx.coroutines.flow.Flow

class SystemRepo : BaseRepository() {
    private val api = HttpManager.create(SystemApi::class.java)

    suspend fun getSystemList(): Flow<DataResult<BaseResponse<ArrayList<SystemDataItem>>>> {
        return executeRepo { api.getSystemList() }
    }

    suspend fun getSystemChildArticleList(
        page: Int,
        cid: Int
    ): Flow<DataResult<BaseResponse<SystemChildData>>> {
        return executeRepo { api.getSystemChildArticleList(page, cid) }
    }

}