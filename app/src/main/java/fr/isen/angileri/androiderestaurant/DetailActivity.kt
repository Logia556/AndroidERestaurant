package fr.isen.angileri.androiderestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.angileri.androiderestaurant.data.Dish
import fr.isen.angileri.androiderestaurant.databinding.ActivityDetailBinding


class DetailActivity: BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var imageCount = 0
    private var itemCount = 1
    private lateinit var dish: Dish
    private var price: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageAdapter = PhotoSlideAdapter(this)
        binding.imagesCarousel.adapter = pageAdapter


        dish = intent.getSerializableExtra("DISH") as Dish
        imageCount = dish.images.count()
        price = dish.prices[0].price.toFloat()

        itemCount = getCurrentDishCount(dish)

        setUpUI(dish)

        binding.btnIncrement.setOnClickListener {
            itemCount++
            updateUI()
        }
        binding.btnDecrement.setOnClickListener {
            if (itemCount>1){
                itemCount--
                updateUI()}
        }
        binding.btnAddToCart.setOnClickListener {
            addToBasket(dish, itemCount)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUI(dish: Dish) {
        binding.dishIngredients.text = dish.ingredients.joinToString {
            it.name
        }
        binding.dishDetailName.text = dish.name
        binding.dishDetailPrice.text = "$price €"
        binding.quantityTextView.text = itemCount.toString()
        binding.btnAddToCart.text = "Total ${price * itemCount} $"
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.quantityTextView.text = itemCount.toString()
        binding.btnAddToCart.text = "${"Total"} $price€"
    }

    override fun onBackPressed() {
        if (binding.imagesCarousel.currentItem == 0) {
            super.onBackPressed()
        } else {
            // Else, select the previous step.
            binding.imagesCarousel.currentItem = binding.imagesCarousel.currentItem - 1
        }
    }

    private fun addToBasket(dish: Dish, itemCount: Int) {
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, itemCount))
        refreshMenu()
        basket.save(this)

    }

    private fun refreshMenu() {
        invalidateOptionsMenu() // refresh l'affichage du menu
    }
    private fun getCurrentDishCount(dish: Dish): Int {
        val basket = Basket.getBasket(this)
        val selectedDish = basket.items.firstOrNull {
            it.dish.name == dish.name
        }
        selectedDish?.let {
            return selectedDish.itemCount
        }
        return 1
    }
    companion object {
        const val BASKET_COUNT = "BASKET_COUNT"
    }

    private inner class PhotoSlideAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = imageCount

        override fun createFragment(position: Int): Fragment = PhotoSlideFragment(dish.images[position])
    }
}