package com.example.mykotlindemo.Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mykotlindemo.Database.Entity.CartTable

@Dao
interface CartDataDao {
    @Insert
    fun insert(cartTable: CartTable?)

    @Update
    fun update(cartTable: CartTable?)

    @Delete
    fun delete(cartTable: CartTable?)

    @Query("SELECT COUNT(*) FROM cart_table ")
    fun getCartCount(): LiveData<Int>?

    @Query("SELECT * FROM cart_table")
    fun allCart(): LiveData<List<CartTable>>?

    @Query("UPDATE cart_table SET item=:item WHERE product_id=:productId ")
    fun upDateCart(productId: String, item: String)

}