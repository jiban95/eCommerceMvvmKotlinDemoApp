package com.example.mvvmkotlinexample.retrofit

import com.example.mvvmkotlinexample.model.ProductListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v2/5def7b172f000063008e0aa2")
    fun getProducts(): Call<ProductListResponse>
}