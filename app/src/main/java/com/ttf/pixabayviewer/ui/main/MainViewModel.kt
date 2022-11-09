package com.ttf.pixabayviewer.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ttf.pixabayviewer.ui.base.BaseViewModel
import com.ttf.pixabayviewer.data.PixabayRepository
import com.ttf.pixabayviewer.data.api.IResult
import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.data.models.SearchImagesSendData
import com.ttf.pixabayviewer.data.network.ConnectionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PixabayRepository,
    @ApplicationContext applicationContext: Context,
    private val connectionLiveData: ConnectionLiveData
) : BaseViewModel<MainNavigator>(applicationContext) {

    val paginationProgress = MutableLiveData(false)
    val query = MutableLiveData("")
    val images = MutableLiveData<MutableList<ImageHit>>(mutableListOf())

    private val startPage = 1
    private val paginationIndexTrigger = 10

    private var isPaginationStopped = false
    private var page = startPage
    private var job: Job? = null

    init {
        search("fruits")

        connectionLiveData.observeForever {isAvailable ->
            if (isAvailable && isPaginationStopped) {
                isPaginationStopped = false
                paginateForQuery(query.value!!)
            }
        }
    }

    var onClickImage: (ImageHit) -> Unit = { item ->
        navigator?.openConfirmDetailsDialog(item)
    }

    var onQuerySubmit: (String) -> Unit = { newQuery ->
        search(newQuery)
    }

    var onScroll: (Int) -> Unit = {
        if (it >= (images.value?.size ?: 0) - paginationIndexTrigger) {
            paginateForQuery(query.value!!)
        }
    }

    fun search(query: String) {
        val filteredQuery = query.trim()
        this.query.postValue(filteredQuery)
        releasePagination()
        paginateForQuery(filteredQuery)
    }

    private fun releasePagination() {
        job?.cancel()
        page = startPage
        images.postValue(mutableListOf())
    }

    private fun stopPagination() {
        isPaginationStopped = true
    }

    @SuppressLint("HardwareIds")
    fun paginateForQuery(query: String) {
        if (job?.isActive == true || isPaginationStopped) return

        job = viewModelScope.launch {
            paginationProgress.postValue(true)
            repository.search(SearchImagesSendData(query, page)).collect { result ->
                when (result.status) {
                    IResult.Status.LOADING -> progress.postValue(true)
                    IResult.Status.FINISH -> progress.postValue(false)
                    IResult.Status.SUCCESS -> {
                        if (result.data == null) {
                            stopPagination()
                        }

                        Log.d("TAG", "page: $page")

                        result.data?.let {
                            val items = images.value!!
                            if (it.hits.isEmpty()) {
                                stopPagination()
                            } else {
                                page++
                                items.addAll(it.hits)
                            }

                            images.postValue(items)
                        }
                    }
                    IResult.Status.ERROR -> {
                        stopPagination()
                    }
                }
            }

            paginationProgress.postValue(false)
        }

    }
}