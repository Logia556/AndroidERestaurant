package fr.isen.angileri.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.isen.angileri.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entriesTextView.setOnClickListener {
            /*Toast.makeText(this, "nouvelle page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            val pageentree = ""
            intent.putExtra("categorie", "pageentree")
            startActivity(intent)*/
            statCategoryActivity(ItemType.ENTREE)
        }

        binding.dishesTextView.setOnClickListener {
            /*val intent = Intent(this, CategoryActivity::class.java)
            val pageplat = ""
            intent.putExtra("categorie", "pageplat")
            startActivity(intent)*/
            statCategoryActivity(ItemType.PLAT)
        }

        binding.dessertTextView.setOnClickListener {
            /*val intent = Intent(this, CategoryActivity::class.java)
            val pagedessert = ""
            intent.putExtra("categorie", "pagedessert")
            startActivity(intent)*/
            statCategoryActivity(ItemType.DESSERT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "dead")
    }

    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}