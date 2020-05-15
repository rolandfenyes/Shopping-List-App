package aut.bme.hu.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import aut.bme.hu.shoppinglist.R
import aut.bme.hu.shoppinglist.data.ShoppingItem
import aut.bme.hu.shoppinglist.data.ShoppingItemCategory
import aut.bme.hu.shoppinglist.data.ShoppingItemCategory.*
import kotlinx.android.synthetic.main.item_shopping_list.view.*

class ShoppingAdapter(private val listener: ShoppingItemClickListener, private val removeListener: ShoppingItemRemovingDialogListener): RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingItem>()

    interface ShoppingItemClickListener {
        fun onItemChanged(item: ShoppingItem)
    }
    interface ShoppingItemRemovingDialogListener {
        fun onShoppingItemRemoved(item: ShoppingItem)
    }

    fun update(shoppingItems: List<ShoppingItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun addItem(shoppingItem: ShoppingItem) {
        items.add(shoppingItem)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(shoppingItem: ShoppingItem) {
        items.remove(shoppingItem)
        notifyDataSetChanged()
    }

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var shoppingItem: ShoppingItem

        fun bind(item: ShoppingItem) {
            shoppingItem = item

            itemView.ivIcon.setImageResource(getImageResource(shoppingItem.category))
            itemView.tvName.text = shoppingItem.name
            itemView.tvDescription.text = shoppingItem.description
            itemView.tvCategory.text = shoppingItem.category.name
            itemView.tvPrice.text = "${shoppingItem.estimatedPrice} Ft"
            itemView.cbIsBought.isChecked = shoppingItem.isBought

            itemView.cbIsBought.setOnCheckedChangeListener { buttonView, isChecked ->
                shoppingItem.isBought = isChecked
                listener.onItemChanged(item)
            }
            itemView.ibRemove.setOnClickListener {
                removeItem(shoppingItem)
                removeListener.onShoppingItemRemoved((shoppingItem))
            }
        }

        @DrawableRes()
        private fun getImageResource(category: ShoppingItemCategory): Int {
            return when(category) {
                FOOD -> R.drawable.groceries
                ELECTRONIC -> R.drawable.lightning
                BOOK -> R.drawable.open_book
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.bind(items[position])
    }

}