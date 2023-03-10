package com.subha.optymize.data.remote.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.subha.optymize.databinding.ItemPagingLoadingBinding


class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: ItemPagingLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progressbar.isVisible = loadState is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_loading, parent, false)
//        return LoadStateViewHolder(view)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemPagingLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

}