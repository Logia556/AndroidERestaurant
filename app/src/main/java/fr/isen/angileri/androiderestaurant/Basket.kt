package fr.isen.angileri.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

class Basket (val items: MutableList<BasketItem>): AppCompatActivity(), Serializable {

    var count: Int = 0
        get() {
            if (this.items.isNotEmpty()) {
                return this.items
                    .map { it.itemCount }
                    .reduce { acc, i -> acc + i }
            }
            return field
        }

    fun addItem(item: BasketItem) {
        val existingItem = items.firstOrNull {
            it.dish.name == item.dish.name
        }
        existingItem?.let {
            existingItem.itemCount = item.itemCount
        } ?: run {
            items.add(item)
        }
    }
    fun clear() {
        items.clear()
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(BASKET_COUNT, count)
        editor.apply()
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            if(jsonFile.exists()) {
                val json = jsonFile.readText()
                return GsonBuilder().create().fromJson(json, Basket::class.java)
            }
            return Basket(mutableListOf())
        }

        const val BASKET_FILE = "basket.json"
        const val BASKET_COUNT = "BASKET_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}