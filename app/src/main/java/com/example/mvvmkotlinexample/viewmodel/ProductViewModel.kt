package com.example.mvvmkotlinexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlinexample.model.ProductListResponse
import com.example.mvvmkotlinexample.repository.ProductRepository
import com.example.mykotlindemo.Database.Entity.CartTable

class ProductViewModel : ViewModel() {

    var produtsLiveData: MutableLiveData<ProductListResponse>? = null

    fun getProductFromServer(): LiveData<ProductListResponse>? {
        produtsLiveData = ProductRepository.getProductApiCall()
        return produtsLiveData
    }

    fun getCartCount(context: Context): LiveData<Int>? {
        return ProductRepository.getCartCount(context)
    }

    fun saveItemToCart(context: Context, product_id: String, item: String) {
        ProductRepository.saveToCart(context, product_id, item)
    }

    fun getCartItem(context: Context): LiveData<List<CartTable>>? {
        return ProductRepository.getAllCart(context)
    }

    fun upDateCartItem(context: Context, productId: String, item: String) {
        ProductRepository.updateCart(context,productId,item)
    }
}