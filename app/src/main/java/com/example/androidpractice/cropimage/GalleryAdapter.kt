package com.example.androidpractice.cropimage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpractice.databinding.ItemImageBinding
import com.example.androidpractice.extension.click

class GalleryAdapter(
    val onItemClick: (Media) -> Unit,
): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private var list: List<Media> = mutableListOf()
    inner class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Media) {
            with(binding) {
                Glide.with(binding.root)
                    .load(data.uri)
                    .into(ivImage)

                root.click {
                    onItemClick.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(list: List<Media>) {
        this.list = list
        notifyDataSetChanged()
    }
}