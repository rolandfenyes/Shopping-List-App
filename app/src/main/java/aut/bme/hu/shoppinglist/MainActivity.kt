package aut.bme.hu.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.hu.shoppinglist.adapter.ShoppingAdapter
import aut.bme.hu.shoppinglist.data.ShoppingItem
import aut.bme.hu.shoppinglist.data.ShoppingListDatabase
import aut.bme.hu.shoppinglist.fragments.NewShoppingItemDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity: AppCompatActivity(), ShoppingAdapter.ShoppingItemClickListener, CoroutineScope by MainScope(), NewShoppingItemDialogFragment.NewShoppingItemDialogListener {

    private lateinit var database: ShoppingListDatabase
    private lateinit var adapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        database = ShoppingListDatabase.getDatabase(applicationContext)

        fab.setOnClickListener {
            NewShoppingItemDialogFragment().show(supportFragmentManager, NewShoppingItemDialogFragment.TAG)
        }

        initRecyclerView()
    }

    override fun onItemChanged(item: ShoppingItem) {
        updateItemInBackgound(item)
    }

    private fun updateItemInBackgound(item: ShoppingItem) = launch {
        withContext(Dispatchers.IO) {
            database.shoppingItemDao().update(item)
        }
    }

    private fun initRecyclerView() {
        adapter = ShoppingAdapter(this)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() = launch {
        val items = withContext(Dispatchers.IO) {
            database.shoppingItemDao().getAll()
        }
        adapter.update(items)
    }

    override fun onShoppingItemCreated(item: ShoppingItem) {
        addItemInBackgound(item)
    }

    private fun addItemInBackgound(item: ShoppingItem) = launch {
        withContext(Dispatchers.IO) {
            database.shoppingItemDao().insert(item)
        }
        adapter.addItem(item)
    }
}
