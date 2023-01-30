package fr.isen.angileri.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.angileri.androiderestaurant.databinding.ActivityBasketBinding
import org.json.JSONObject

class BasketActivity: AppCompatActivity(), BasketCellClickListener {
    private lateinit var binding: ActivityBasketBinding
    private lateinit var basket: Basket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket = Basket.getBasket(this)


    }

    private fun reloadData() {
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(Basket.getBasket(this).items, this)
    }

    override fun onDeleteItem(basketItem: BasketItem) {
        val itemToDelete = basket.items.firstOrNull {
            it.dish.name == basketItem.dish.name
        }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData()
    }
    private fun sendOrder(user_id: Int) {
        val message = basket.items.joinToString {
            it.dish.name + " " + it.itemCount
        }
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/order"
        val postData = createPostData(user_id, message)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            postData,
            Response.Listener {
                Toast.makeText(this, "Votre commande a été bien passée.", Toast.LENGTH_SHORT).show()
                basket.clear()
            },
            Response.ErrorListener { error ->
                onFailure(error)
            }
        )
        queue.add(request)
    }

    private fun createPostData(id_user: Int, message: String): JSONObject {
        val postData = JSONObject()
        postData.put("id_shop", "1")
        postData.put("id_user", id_user)
        postData.put("msg", message)
        return postData
    }

    private fun onFailure(error: VolleyError) {
        Log.d("request", String(error.networkResponse.data))
    }

}