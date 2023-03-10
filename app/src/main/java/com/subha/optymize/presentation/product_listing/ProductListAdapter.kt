package com.subha.optymize.presentation.product_listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.subha.optymize.R
import com.subha.optymize.data.model.Product
import com.subha.optymize.databinding.ItemProductlistBinding


class ProductListAdapter(context: Context, val noti: clickListener) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var mData = ArrayList<Product>()
    var context = context
    var notify = noti


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var bind = ItemProductlistBinding.bind(itemView)

        fun update(
            get: Product,
            context: Context,
            position: Int,
            noti: clickListener
        ) {

            Glide.with(context)
                .load(get.thumbnail) // image url
                .fitCenter()
                .into(bind.ivProduct)

            bind.tvProductName.text = get.title
            bind.root.setOnClickListener {
                noti.enter(get.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View

        v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productlist, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(mData[position], context, position, notify)
    }

    // return the size of languageList
    override fun getItemCount(): Int = mData.size


    fun setData(list: ArrayList<Product>) {
        mData = list
        notifyDataSetChanged()
    }

    interface clickListener {
        fun enter(
            name: String
        )


    }

}