package com.example.mvvmkotlinexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmkotlinexample.R
import com.example.mvvmkotlinexample.databinding.CartLayoutBinding
import com.example.mvvmkotlinexample.model.Common
import com.example.mvvmkotlinexample.model.ProductsItem
import com.example.mykotlindemo.Database.Entity.CartTable
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.product_layout.view.productImage
import kotlinx.android.synthetic.main.product_layout.view.productName
import kotlinx.android.synthetic.main.product_layout.view.productPrice

class CartAdapter(
    val context: Context,
    val itemCounter: ItemCounter,
    val cartDateList: List<CartTable>
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    lateinit var cartLayoutBinding: CartLayoutBinding
    var gson: Gson = Gson()
    lateinit var productsItem: ProductsItem

    init {
        Common.totalPrice = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        cartLayoutBinding = CartLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(cartLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        productsItem = gson.fromJson(cartDateList.get(position).item, ProductsItem::class.java)
        holder.bindItems(context, itemCounter, productsItem)
    }

    override fun getItemCount(): Int {
        return cartDateList.size
    }

    class ViewHolder(itemView: CartLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItems(
            context: Context,
            itemCounter: ItemCounter,
            productsItem: ProductsItem
        ) {
            try {
                var price: String = productsItem.price.toString()
                    .replace(context.getString(R.string.Rs), "")
                var actual_price: Int = price.replace(",", "").toInt()

                if (productsItem.quantity == 1) {
                    productsItem.special = actual_price.toString()
                }

                Common.totalPrice = Common.totalPrice + actual_price
                itemCounter.totalPrice(Common.totalPrice)
                itemView.productName.text = productsItem.name
                itemView.productPrice.text = context.getString(R.string.Rs) + actual_price
                itemView.productCount.text = productsItem.quantity.toString()

                Glide.with(context)
                    .load(productsItem.image)
                    .into(itemView.productImage)

                var count: Int = itemView.productCount.text.toString().toInt()

                itemView.add_btn.setOnClickListener {
                    count += 1
                    if (count >= 1) {
                        productsItem.price = (count * productsItem.special!!.toInt()).toString()
                        productsItem.quantity = count
                        itemCounter.increment(productsItem)
                    }
                }

                itemView.subtract_btn.setOnClickListener {
                    count -= 1
                    if (count >= 1) {
                        productsItem.price = (count * productsItem.special!!.toInt()).toString()
                        productsItem.quantity = count
                        itemCounter.decrement(productsItem)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    interface ItemCounter {
        fun increment(productsItem: ProductsItem)
        fun decrement(productsItem: ProductsItem)
        fun totalPrice(totalPrice: Int)
    }
}