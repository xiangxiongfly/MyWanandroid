package com.core.wanandroid.login

import com.core.app.DataResult
import com.core.app.base.BaseRepository
import com.core.app.entity.BaseResponse
import com.core.app.network.HttpManager
import com.core.wanandroid.api.LoginApi
import com.core.wanandroid.bean.login.LoginData
import kotlinx.coroutines.flow.Flow

class LoginRepo : BaseRepository() {
    val api = HttpManager.create(LoginApi::class.java)

    suspend fun login(
        username: String,
        password: String
    ): Flow<DataResult<BaseResponse<LoginData>>> {
        return executeRepo {
            api.login(username, password)
        }
    }

}