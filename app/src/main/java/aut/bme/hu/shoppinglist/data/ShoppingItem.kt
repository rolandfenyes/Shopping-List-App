package aut.bme.hu.shoppinglist.data

data class ShoppingItem(
    var name: String,
    var category: String,
    var description: String,
    var estimatedPrice: Int,
    var isBought: Boolean
)