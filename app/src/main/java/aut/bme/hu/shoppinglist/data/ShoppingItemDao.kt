package aut.bme.hu.shoppinglist.data

import androidx.room.*

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shoppingitem")
    suspend fun getAll(): List<ShoppingItem>

    @Insert
    suspend fun insert(shoppingItem: ShoppingItem): Long

    @Update
    suspend fun update(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)
}