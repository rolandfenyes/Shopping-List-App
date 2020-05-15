package aut.bme.hu.shoppinglist.data

import androidx.room.*

@Dao
interface ShoppingItemDao {
    @Query(value = "SELECT * FROM shoppingitem")
    fun getAll(): List<ShoppingItem>

    @Insert
    fun insert(shoppingItem: ShoppingItem): Long

    @Update
    fun update(shoppingItem: ShoppingItem)

    @Delete
    fun delete(shoppingItem: ShoppingItem)
}