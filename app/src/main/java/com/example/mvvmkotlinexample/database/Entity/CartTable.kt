package com.example.mykotlindemo.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
class CartTable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var product_id: String? = null
    var item: String? = null

    constructor(product_id: String, item: String?) {
        this.product_id = product_id
        this.item = item
    }
}