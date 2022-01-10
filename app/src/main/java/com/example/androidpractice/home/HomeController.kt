package com.example.androidpractice.home

import android.content.Context
import com.airbnb.epoxy.EpoxyController

class HomeController(
    private val context: Context,
    private val controllerListener: HomeControllerListener
) : EpoxyController() {
    private val uiComponentName = UiComponentName.values()

    fun setData() {
        requestModelBuild()
    }
    override fun buildModels() {
        uiComponentName.forEachIndexed { index, uiComponentName ->
            add(
                ComponentTitleEpoxyItem_()
                    .id("UiComponentTitle$index")
                    .uiComponentName(uiComponentName)
                    .listener(controllerListener)
            )
        }
    }

}

interface HomeControllerListener : ComponentTitleEpoxyItem.Listener