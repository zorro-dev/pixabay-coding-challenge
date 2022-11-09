package com.ttf.pixabayviewer.ui.main

import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.ui.base.BaseNavigator

interface MainNavigator : BaseNavigator {

    fun openDetailsFragment(image: ImageHit)

    fun openConfirmDetailsDialog(image: ImageHit)

}