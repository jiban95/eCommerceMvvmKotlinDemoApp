package com.example.mvvmkotlinexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmkotlinexample.databinding.ProductLayoutBinding
import com.example.mvvmkotlinexample.model.ProductsItem
import kotlinx.android.synthetic.main.product_layout.view.*

class ProductAdapter(
    val context: Context,
    val addItemToCart: AddItemToCart,
    val productList: List<ProductsItem>
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    lateinit var productLayoutBinding: ProductLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        productLayoutBinding =
            ProductLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(productLayoutBinding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItems(context, productList.get(position))
        holder.itemView.setOnClickListener {
            addItemToCart.addItemListener(position, productList)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: ProductLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {

        fun bindItems(context: Context, productsItem: ProductsItem) {
            try {
                itemView.productName.text = productsItem.name
                itemView.productPrice.text = productsItem.price
                itemView.productDes.text = productsItem.description

                Glide.with(context)
                    .load(productsItem.image)
                    .into(itemView.productImage)
            } catch (e: Exception) {
            }
        }
    }

    interface AddItemToCart {
        fun addItemListener(position: Int, productList: List<ProductsItem>)
    }
}