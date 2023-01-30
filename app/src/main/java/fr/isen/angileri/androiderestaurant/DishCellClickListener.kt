package fr.isen.angileri.androiderestaurant

import fr.isen.angileri.androiderestaurant.data.Dish

interface DishCellClickListener {
    fun onCellClickListener(data: Dish)
}