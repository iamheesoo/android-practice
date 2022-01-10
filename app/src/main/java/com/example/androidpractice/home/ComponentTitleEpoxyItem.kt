package com.example.androidpractice.home

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.androidpractice.R
import com.example.androidpractice.databinding.ItemComponentTitleBinding
import com.example.androidpractice.extension.click

@EpoxyModelClass(layout = R.layout.item_component_title)
abstract class ComponentTitleEpoxyItem : EpoxyModelWithHolder<ComponentTitleEpoxyItemHolder>() {

    interface Listener {
        fun onTitleClick(name: UiComponentName)
    }

    @EpoxyAttribute
    var uiComponentName: UiComponentName? = null

    @EpoxyAttribute
    var listener: Listener? = null

    override fun createNewHolder(): ComponentTitleEpoxyItemHolder {
        return ComponentTitleEpoxyItemHolder()
    }

    override fun bind(holder: ComponentTitleEpoxyItemHolder) {
        holder.uiComponentName = uiComponentName
        holder.listener = listener

        with (holder.viewBinding) {
            tvTitle.text = uiComponentName?.name ?: ""
        }
    }

}

class ComponentTitleEpoxyItemHolder : EpoxyHolder() {
    var uiComponentName: UiComponentName? = null
    var listener: ComponentTitleEpoxyItem.Listener? = null

    lateinit var viewBinding: ItemComponentTitleBinding

    override fun bindView(itemView: View) {
        viewBinding = ItemComponentTitleBinding.bind(itemView)

        with (viewBinding) {
            tvTitle.click {
                uiComponentName?.let {
                    listener?.onTitleClick(it)
                }
            }
        }
    }

}