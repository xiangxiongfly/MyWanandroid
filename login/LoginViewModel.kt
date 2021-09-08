package com.core.wanandroid.login

import androidx.lifecycle.viewModelScope
import com.core.app.DataResult
import com.core.app.base.BaseViewModel
import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.login.LoginData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    private val repo = LoginRepo()

    private val _loginFlow = MutableStateFlow<DataResult<BaseResponse<LoginData>>>(DataResult.None)
    val loginFlow = _loginFlow.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repo.login(username, password).collect {
                _loginFlow.value = it
            }
        }
    }

}