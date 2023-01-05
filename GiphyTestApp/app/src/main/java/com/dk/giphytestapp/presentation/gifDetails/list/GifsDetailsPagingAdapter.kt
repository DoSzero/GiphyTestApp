package com.dk.giphytestapp.presentation.gifDetails.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dk.giphytestapp.databinding.GifDetailsPageBinding

import com.dk.giphytestapp.presentation.gifGrid.model.GifImageGridItemModel

class GifsDetailsPagingAdapter() :
    PagingDataAdapter<GifImageGridItemModel,GifsDetailsPagingAdapter.GifsViewHolder>(GifImageDiffUtil()) {

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = GifDetailsPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    class GifsViewHolder(private val binding: GifDetailsPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GifImageGridItemModel?) {
            item?.also {
                Glide.with(binding.gifImage).load(it.fullGifUrl).into(binding.gifImage)
            } ?: run {
                binding.gifImage.setImageBitmap(null)
            }
        }

    }

    private class GifImageDiffUtil : DiffUtil.ItemCallback<GifImageGridItemModel>() {
        override fun areItemsTheSame(oldItem: GifImageGridItemModel, newItem: GifImageGridItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifImageGridItemModel, newItem: GifImageGridItemModel): Boolean {
            return oldItem == newItem
        }
    }
}
