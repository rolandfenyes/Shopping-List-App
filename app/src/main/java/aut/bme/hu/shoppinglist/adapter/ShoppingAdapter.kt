package aut.bme.hu.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.bme.hu.shoppinglist.R
import aut.bme.hu.shoppinglist.data.ShoppingItem

class ShoppingAdapter: RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingItem>()

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}