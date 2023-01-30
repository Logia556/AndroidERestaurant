package fr.isen.angileri.androiderestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.angileri.androiderestaurant.data.Dish
import fr.isen.angileri.androiderestaurant.databinding.DishCellBinding

class CategoryAdapter(
    private val entries: List<Dish>,
    private val cellClickListener: (Dish) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.DishViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return DishViewHolder(
            DishCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.dishTitle.text = entries[position].name.take(40)
        holder.dishPrice.text = "${entries[position].prices[0].price}€"
        if (entries[position].images[0].isNotEmpty()) {
            Picasso.get().load(entries[position].images[0]).into(holder.dishImageView)
        }
        holder.layout.setOnClickListener {
            cellClickListener(entries[position])
        }
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishViewHolder(dishBinding: DishCellBinding) : RecyclerView.ViewHolder(dishBinding.root) {

        val dishTitle: TextView = dishBinding.dishTitle
        val dishPrice: TextView = dishBinding.dishPrice
        val dishImageView: ImageView = dishBinding.dishImageView
        val layout: CardView = dishBinding.root
    }
}