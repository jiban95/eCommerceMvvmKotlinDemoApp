package com.example.mvvmkotlinexample.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmkotlinexample.model.ProductListResponse
import com.example.mvvmkotlinexample.retrofit.RetrofitClient
import com.example.mykotlindemo.Database.Entity.CartTable
import com.example.mykotlindemo.Database.VajroDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    val productListResponse = MutableLiveData<ProductListResponse>()
    var vajroDatabase: VajroDatabase? = null

    fun getProductApiCall(): MutableLiveData<ProductListResponse> {

        val call = RetrofitClient.apiInterface.getProducts()

        call.enqueue(object : Callback<ProductListResponse> {
            override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {

                Log.i("DEBUGValue", t.message.toString())
            }

            override fun onResponse(
                call: Call<ProductListResponse>,
                response: Response<ProductListResponse>
            ) {
                Log.i("DEBUGValue", response.body().toString())

                productListResponse.postValue(response.body())
            }
        })
        return productListResponse
    }

    fun getCartCount(context: Context): LiveData<Int>? {
        vajroDatabase = VajroDatabase.getInstance(context)
        return vajroDatabase!!.cartDao().getCartCount()
    }

    fun saveToCart(context: Context, product_id: String, item: String) {
        vajroDatabase = VajroDatabase.getInstance(context)
        vajroDatabase!!.cartDao().insert(CartTable(product_id, item))
    }

    fun getAllCart(context: Context): LiveData<List<CartTable>>? {
        vajroDatabase = VajroDatabase.getInstance(context)
        return vajroDatabase!!.cartDao().allCart()
    }

    fun updateCart(context: Context, productId: String, item: String) {
        vajroDatabase = VajroDatabase.getInstance(context)
        vajroDatabase!!.cartDao().upDateCart(productId, item)
    }
}