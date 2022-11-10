package com.ttf.pixabayviewer.ui.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel<T : BaseNavigator>(application: Context) : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<Boolean> = MutableLiveData(false)
    var context: WeakReference<Context> = WeakReference(application)
    var navigator: T? = null

    fun onClickClose() {
        navigator?.close()
    }

    fun getBase(): BaseViewModel<T> = this
}