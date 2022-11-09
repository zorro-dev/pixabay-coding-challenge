package com.ttf.pixabayviewer.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ttf.pixabayviewer.ui.base.BaseViewModel
import com.ttf.pixabayviewer.data.PixabayRepository
import com.ttf.pixabayviewer.data.api.IResult
import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.data.models.SearchImagesSendData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PixabayRepository,
    @ApplicationContext applicationContext: Context,
) : BaseViewModel<MainNavigator>(applicationContext) {

    val paginationProgress = MutableLiveData(false)
    val query = MutableLiveData("")
    val images = MutableLiveData<MutableList<ImageHit>>(mutableListOf())

    private var page = 0
    private var job: Job? = null

    init {
        search("fruits")
    }

    var onClickImage: (ImageHit) -> Unit = { item ->
        navigator?.openConfirmDetailsDialog(item)
    }

    var onQuerySubmit: (String) -> Unit = { newQuery ->
        search(newQuery)
    }

    var onScroll: (Int) -> Unit = {
        if (it >= (images.value?.size ?: 0) - 10) {
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
        page = 0
        images.postValue(mutableListOf())
    }

    private fun stopPagination() {
        page = -2
    }

    @SuppressLint("HardwareIds")
    fun paginateForQuery(query: String) {
        if (job?.isActive == true || page == -2) return

        job = viewModelScope.launch {
            paginationProgress.postValue(true)
            page++

            repository.search(SearchImagesSendData(query, page)).collect { result ->
                when (result.status) {
                    IResult.Status.LOADING -> progress.postValue(true)
                    IResult.Status.FINISH -> progress.postValue(false)
                    IResult.Status.SUCCESS -> {
                        if (result.data == null) {
                            stopPagination()
                        }

                        result.data?.let {
                            val items = images.value!!
                            if (it.hits.isEmpty()) {
                                stopPagination()
                            } else {
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