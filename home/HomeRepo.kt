package com.core.wanandroid.home

import com.core.app.DataResult
import com.core.app.base.BaseRepository
import com.core.app.entity.BaseResponse
import com.core.app.network.HttpManager
import com.core.wanandroid.api.HomeApi
import com.core.wanandroid.bean.home.ArticleData
import com.core.wanandroid.bean.home.BannerDataItem
import kotlinx.coroutines.flow.Flow


class HomeRepo : BaseRepository() {

    private val api = HttpManager.create(HomeApi::class.java)

    suspend fun getArticle(page: Int): Flow<DataResult<BaseResponse<ArticleData>>> {
        return executeRepo { api.getHomeArticle(page) }
    }

    suspend fun getBanner(): Flow<DataResult<BaseResponse<ArrayList<BannerDataItem>>>> {
        return executeRepo { api.getBanner() }
    }

    suspend fun collect(id: Int): Flow<DataResult<BaseResponse<Any>>> {
        return executeRepo { api.collect(id) }
    }

    suspend fun unCollect(id: Int): Flow<DataResult<BaseResponse<Any>>> {
        return executeRepo { api.uncollect(id) }
    }
}