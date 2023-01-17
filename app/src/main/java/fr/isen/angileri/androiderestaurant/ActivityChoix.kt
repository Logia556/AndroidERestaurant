package fr.isen.angileri.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityChoix : AppCompatActivity(){
    lateinit var activeElement: List<ActiveElement>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix)

        val category = intent.getStringExtra("categorie")
        val CategoryDisplay = findViewById<TextView>(R.id.CategoryDisplay)
        CategoryDisplay.text = category
        actionBar?.title = category

        val rvActiveElement = findViewById<View>(R.id.rvActiveElement) as RecyclerView
        activeElement = ActiveElement.createActiveElementList()
        val adapter = ActiveElementAdapter(activeElement)
        rvActiveElement.adapter = adapter
        rvActiveElement.layoutManager = LinearLayoutManager(this)


    }


}