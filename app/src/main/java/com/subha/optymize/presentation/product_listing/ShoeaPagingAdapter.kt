package com.subha.optymize.presentation.product_listing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.subha.optymize.R
import com.subha.optymize.data.model.Product
import com.subha.optymize.databinding.ItemProductlistBinding


class ShoeaPagingAdapter(context: Context, val noti: clickListener) : PagingDataAdapter<Product,
        ShoeaPagingAdapter.ShoeaPagingViewHolder>(diffCallback) {


    var context = context
    var notify = noti

    inner class ShoeaPagingViewHolder(
        val binding: ItemProductlistBinding
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeaPagingViewHolder {
        return ShoeaPagingViewHolder(
            ItemProductlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoeaPagingViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                Glide.with(context)
                    .load(data!!.thumbnail)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_image_24)
                    .centerInside()
                    .into(ivProduct)
                tvProductName.text = "${data?.title}"
                tvCategory.text = data.category
                tvStock.text = "${data.stock} left"
                tvBrand.text = "Brand: ${data.brand}"
                tvPrice.text = "$${data.price}"
                cardView.setOnClickListener {
                    notify.item(data.id.toString())
                }
            }
        }

    }

    interface clickListener {
        fun item(id: String)
    }
}