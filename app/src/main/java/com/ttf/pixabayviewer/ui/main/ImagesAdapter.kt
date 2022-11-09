package com.ttf.pixabayviewer.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ttf.pixabayviewer.R
import com.ttf.pixabayviewer.bind.Base.setConstraintSizedImage
import com.ttf.pixabayviewer.bind.Base.setStringOrDefaultRes
import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.databinding.ItemImageBinding

class ImagesAdapter(private val onClick: (ImageHit) -> Unit) :
    ListAdapter<ImageHit, RecyclerView.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ImageHit>() {
            override fun areItemsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_image,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EventViewHolder) {
            holder.bind(getItem(position))
        }
    }

    internal inner class EventViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ImageHit) {
            binding.image.setConstraintSizedImage(item.previewUrl, item.previewSize)
            binding.size.text = "${item.imageWidth}x${item.imageHeight}"

            binding.text1.setStringOrDefaultRes(item.user, R.string.unknown_author)

            binding.text2.apply {
                text = item.tags
                isGone = item.tags.isNullOrEmpty()
            }

            binding.itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }
}