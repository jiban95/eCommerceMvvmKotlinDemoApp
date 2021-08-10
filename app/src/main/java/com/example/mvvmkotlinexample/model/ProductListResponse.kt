package com.example.mvvmkotlinexample.model

data class ProductListResponse(
	var products: List<ProductsItem>? = null
)

data class ProductsItem(
	var image: String? = null,
	var images: List<Any?>? = null,
	var quantity: Int? = null,
	var thumb: String? = null,
	var description: String? = null,
	var zoomThumb: String? = null,
	var special: String? = null,
	var price: String? = null,
	var product_id: String? = null,
	var name: String? = null,
	var options: List<Any?>? = null,
	var id: String? = null,
	var href: String? = null,
	var sku: String? = null
)

