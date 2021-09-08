package com.core.wanandroid.bean.system

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SystemDataItem(
    val children: ArrayList<Children>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable

@Parcelize
data class Children(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var selectChildren: Int = 0
) : Parcelable