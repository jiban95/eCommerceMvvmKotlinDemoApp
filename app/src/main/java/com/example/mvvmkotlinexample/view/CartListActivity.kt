package com.example.mvvmkotlinexample.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmkotlinexample.R
import com.example.mvvmkotlinexample.adapter.CartAdapter
import com.example.mvvmkotlinexample.databinding.ActivityCartListBinding
import com.example.mvvmkotlinexample.model.ProductsItem
import com.example.mvvmkotlinexample.viewmodel.ProductViewModel
import com.example.mykotlindemo.Database.Entity.CartTable
import com.google.gson.Gson

class CartListActivity : AppCompatActivity(), CartAdapter.ItemCounter {
    lateinit var viewBinding: ActivityCartListBinding
    lateinit var context: Context
    lateinit var productViewModel: ProductViewModel
    lateinit var cartAdapter: CartAdapter
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        viewBinding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        gson = Gson()
        context = this@CartListActivity

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.getCartItem(context)
            ?.observe(this, androidx.lifecycle.Observer { cartData ->
                cartData?.let {
                    supportActionBar!!.title = "My Cart " + "(" + cartData.size + ")"
                    setDataAdapter(cartData)
                }
            })
        viewBinding.toolbar.setNavigationOnClickListener({ v -> onBackPressed() })

    }

    private fun setDataAdapter(cartDateList: List<CartTable>) {
        viewBinding.cartRecycler.layoutManager = LinearLayoutManager(this)
        cartAdapter =
            CartAdapter(context, context as CartAdapter.ItemCounter, cartDateList)
        viewBinding.cartRecycler.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()
    }

    override fun increment(
        productsItem: ProductsItem
    ) {
        productViewModel.upDateCartItem(
            context,
            productsItem.product_id.toString(),
            gson.toJson(productsItem)
        )
    }

    override fun decrement(
        productsItem: ProductsItem
    ) {
        productViewModel.upDateCartItem(
            context,
            productsItem.product_id.toString(),
            gson.toJson(productsItem)
        )
    }

    override fun totalPrice(totalPrice: Int) {
        viewBinding.textPrice.text = getString(R.string.Rs) + totalPrice
        viewBinding.bottomView.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}