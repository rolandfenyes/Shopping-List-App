package aut.bme.hu.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

enum class ShoppingItemCategory {
    FOOD,
    ELECTRONIC,
    BOO;

    companion object {
        @TypeConverter @JvmStatic
        fun getByOrdinal(ordinal: Int): ShoppingItemCategory? {
            var ret: ShoppingItemCategory? = null
            for (e in values()) {
                if (e.ordinal == ordinal) {
                    ret = e
                    break
                }
            }
            return ret
        }

        @TypeConverter @JvmStatic
        fun toInt(category: ShoppingItemCategory): Int {
            return category.ordinal
        }
    }
}

@Entity(tableName = "shoppingitem")
data class ShoppingItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "category") var category: ShoppingItemCategory,
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "estimated_price") var estimatedPrice: Int = 0,
    @ColumnInfo(name = "is_bought") var isBought: Boolean = false
)