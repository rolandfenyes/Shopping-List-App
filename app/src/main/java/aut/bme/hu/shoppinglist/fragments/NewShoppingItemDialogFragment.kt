package aut.bme.hu.shoppinglist.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import aut.bme.hu.shoppinglist.R
import aut.bme.hu.shoppinglist.data.ShoppingItem
import aut.bme.hu.shoppinglist.data.ShoppingItemCategory
import kotlinx.android.synthetic.main.dialog_new_shopping_item.view.*
import kotlinx.coroutines.NonCancellable.cancel

class NewShoppingItemDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }

    private lateinit var listener: NewShoppingItemDialogListener
    private lateinit var contentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: FragmentActivity = getActivity()!!
        listener = if (activity is NewShoppingItemDialogListener) {
            activity
        } else {
            throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        contentView = getContentView()


        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.new_shopping_item)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if(isValid()) {
                    listener.onShoppingItemCreated(getShoppingItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid(): Boolean {
        return contentView.etName.text.isNotEmpty()
    }

    private fun getShoppingItem(): ShoppingItem {
        return ShoppingItem(
            name = contentView.etName.text.toString(),
            description = contentView.etDescription.text.toString(),
            estimatedPrice = contentView.etEstimatedPrice.text.toString().toIntOrNull() ?: 0,
            category = ShoppingItemCategory.getByOrdinal(contentView.spCategory.selectedItemPosition) ?: ShoppingItemCategory.BOOK,
            isBought = contentView.cbAlreadyPurchased.isChecked
        )
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_shopping_item, null)
        contentView.spCategory.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.category_items))
        return contentView
    }

    interface NewShoppingItemDialogListener {
        fun onShoppingItemCreated(item: ShoppingItem)
    }
}