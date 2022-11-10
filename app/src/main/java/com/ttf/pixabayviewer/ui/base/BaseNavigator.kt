package com.ttf.pixabayviewer.ui.base

import androidx.annotation.StringRes

interface BaseNavigator {

    fun showToast(msg: String)

    fun showToast(@StringRes msg: Int)

    fun showSnackBar(msg: String)

    fun showSnackBar(@StringRes msg: Int)

    fun close()

}