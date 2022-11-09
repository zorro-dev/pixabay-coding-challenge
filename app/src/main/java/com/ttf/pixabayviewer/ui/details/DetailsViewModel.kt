package com.ttf.pixabayviewer.ui.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ttf.pixabayviewer.ui.base.BaseViewModel
import com.ttf.pixabayviewer.data.models.ImageHit
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context,
) : BaseViewModel<DetailsNavigator>(applicationContext) {

    val image = MutableLiveData<ImageHit>()


}