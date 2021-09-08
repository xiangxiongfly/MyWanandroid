package com.core.wanandroid.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.core.app.DataResult
import com.core.app.base.BaseViewModel
import com.core.app.entity.BaseResponse
import com.core.wanandroid.bean.CollectUiState
import com.core.wanandroid.bean.home.ArticleData
import com.core.wanandroid.bean.home.BannerDataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val homeRepo = HomeRepo()

    private var page = 0

    private val _articleFlow =
        MutableStateFlow<DataResult<BaseResponse<ArticleData>>>(DataResult.None)
    val articleFlow = _articleFlow.asStateFlow()

    private val _bannerFlow =
        MutableStateFlow<DataResult<BaseResponse<ArrayList<BannerDataItem>>>>(DataResult.None)
    val bannerFlow = _bannerFlow.asStateFlow()

    private val _collectLiveData = MutableLiveData<CollectUiState>()
    val collectLiveData = _collectLiveData


    fun getArticle(isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh)
                page = 0

            homeRepo.getArticle(page).collect {
                when (it) {
                    is DataResult.Success -> {
                        page++
                        it.response.isRefresh = isRefresh
                        _articleFlow.value = it
                    }
                    else -> {
                        _articleFlow.value = it
                    }
                }
            }
        }
    }

    fun getBanner() {
        viewModelScope.launch {
            homeRepo.getBanner().collect {
                _bannerFlow.value = it
            }
        }
    }

    fun collect(id: Int, position: Int) {
        viewModelScope.launch {
            homeRepo.collect(id).collect {
                when (it) {
                    is DataResult.Success -> {
                        _collectLiveData.value = CollectUiState(true, true, id, position, "收藏成功")
                    }
                    is DataResult.Error -> {
                        _collectLiveData.value =
                            CollectUiState(false, false, id, position, "收藏失败")
                    }
                }
            }
        }
    }

    fun unCollect(id: Int, position: Int) {
        viewModelScope.launch {
            homeRepo.unCollect(id).collect {
                when (it) {
                    is DataResult.Success -> {
                        _collectLiveData.value = CollectUiState(true, false, id, position, "取消成功")
                    }
                    is DataResult.Error -> {
                        _collectLiveData.value =
                            CollectUiState(false, true, id, position, "取消收藏失败")
                    }
                }
            }
        }
    }

}