package com.example.mvvmkotlinexample.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmkotlinexample.R
import com.example.mvvmkotlinexample.adapter.ProductAdapter
import com.example.mvvmkotlinexample.databinding.ActivityProductListBinding
import com.example.mvvmkotlinexample.model.ProductsItem
import com.example.mvvmkotlinexample.viewmodel.ProductViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_product_list.*
import java.util.*

class ProductListActivity : AppCompatActivity(), ProductAdapter.AddItemToCart {
    lateinit var badgeCount: AppCompatTextView
    lateinit var badgeFrame: FrameLayout
    lateinit var context: Context
    lateinit var gson: Gson
    lateinit var productViewModel: ProductViewModel
    lateinit var viewBinding: ActivityProductListBinding
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)
        gson = Gson()
        context = this@ProductListActivity

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewBinding.progressBar.show()

        productViewModel.getProductFromServer()!!.observe(this,
            androidx.lifecycle.Observer { produtsListResponse ->
                viewBinding.progressBar.hide()
                if (produtsListResponse != null) {
                    produtsListResponse.products?.let { setDataAdapter(it) }
                } else {
                    // Something went wrong
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        val menuItem = menu.findItem(R.id.cart)
        val actionView: View = menuItem.actionView
        badgeFrame = actionView.findViewById(R.id.cartMenu) as FrameLayout
        badgeCount = actionView.findViewById(R.id.cart_badge) as AppCompatTextView
        setUpBadgeCount()
        badgeFrame.setOnClickListener {
            startActivity(Intent(context, CartListActivity::class.java))
        }
        return true
    }

    private fun setUpBadgeCount() {
        productViewModel.getCartCount(context)
            ?.observe(this, androidx.lifecycle.Observer { data ->
                data?.let { badgeCount.text = it.toString() }
            })
    }

    private fun setDataAdapter(productList: List<ProductsItem>) {
        viewBinding.productRecycler.layoutManager = GridLayoutManager(this, 2)
        productAdapter =
            ProductAdapter(context, context as ProductAdapter.AddItemToCart, productList)
        viewBinding.productRecycler.adapter = productAdapter
        productAdapter.notifyDataSetChanged()
    }

    override fun addItemListener(position: Int, productList: List<ProductsItem>) {
        productViewModel.saveItemToCart(
            context,
            productList.get(position).product_id.toString(),
            gson.toJson(productList.get(position))
        )
        Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
    }
}


