package com.ttf.pixabayviewer.ui.base

interface BaseNavigator {

    fun showToast(msg: String?)

    fun showToast(msg: Int)

    fun showSnackBar(msg: String?, success: Boolean = false)

    fun showSnackBar(msg: Int, success: Boolean = false)

    fun close()

}