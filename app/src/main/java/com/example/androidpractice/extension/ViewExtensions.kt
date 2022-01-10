package com.example.androidpractice.extension

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


fun View.click(call: () -> Unit) {
    this.post {
        val scope = ViewTreeLifecycleOwner.get(this)?.lifecycleScope
        if (scope == null) {
            val activity = this.context as? AppCompatActivity
            activity?.let {
                this.clicks().throttleFirst(1000L).onEach {
                    call.invoke()
                }.launchIn(it.lifecycleScope)
            }
        } else {
            this.clicks().throttleFirst(1000L).onEach {
                call.invoke()
            }.launchIn(scope)
        }
    }
}