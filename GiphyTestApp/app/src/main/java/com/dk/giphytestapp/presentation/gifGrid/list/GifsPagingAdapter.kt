package com.dk.giphytestapp.presentation.gifGrid.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dk.giphytestapp.databinding.GifGridItemBinding
import com.dk.giphytestapp.presentation.gifGrid.model.GifImageGridItemModel

class GifsPagingAdapter(
    private val onItemClick: (GifImageGridItemModel, Int) -> Unit,
    private val onItemLongClick: (GifImageGridItemModel) -> Unit
) :
    PagingDataAdapter<GifImageGridItemModel, GifsPagingAdapter.GifsViewHolder>(GifImageDiffUtil()) {

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = GifGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding, onItemClick, onItemLongClick)
    }

    class GifsViewHolder(
        private val binding: GifGridItemBinding,
        private val onItemClick: (GifImageGridItemModel, Int) -> Unit,
        private val onItemLongClick: (GifImageGridItemModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: GifImageGridItemModel? = null

        init {
            binding.root.setOnLongClickListener {
                currentItem?.also {
                    onItemLongClick(it)
                }
                true
            }
            binding.root.setOnClickListener {
                currentItem?.also { item ->
                    onItemClick(item, absoluteAdapterPosition)

                }
            }
        }

        fun bind(item: GifImageGridItemModel?) {
            currentItem = item
            item?.also {
                Glide.with(binding.gif).load(it.previewUrl).into(binding.gif)
            } ?: run {
                binding.gif.setImageBitmap(null)
            }
        }

    }

    private class GifImageDiffUtil : DiffUtil.ItemCallback<GifImageGridItemModel>() {
        override fun areItemsTheSame(
            oldItem: GifImageGridItemModel,
            newItem: GifImageGridItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GifImageGridItemModel,
            newItem: GifImageGridItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}
