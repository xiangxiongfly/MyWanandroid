package com.core.wanandroid.bean

data class CollectUiState(
    var isSuccess: Boolean = false,
    var collect: Boolean = false,
    var id: Int = -1,
    var position: Int = -1,
    var errMsg: String = ""
)