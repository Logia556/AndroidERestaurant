package fr.isen.angileri.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActiveElement(val name: String) {

    companion object {

        fun createActiveElementList(): List<ActiveElement> {


            return listOf(ActiveElement("banana"), ActiveElement("weeeeeeeee"), ActiveElement("lulululululululu"))
        }
    }
}

class ActiveElementAdapter (private val mActiveElement: List<ActiveElement>): RecyclerView.Adapter<ActiveElementAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.activeElementId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val activeElementView = inflater.inflate(R.layout.active_element, parent, false)
        return ViewHolder(activeElementView)
    }

    override fun onBindViewHolder(viewHolder: ActiveElementAdapter.ViewHolder, position: Int) {
        val activeElement: ActiveElement = mActiveElement.get(position)
        val textView = viewHolder.nameTextView
        textView.setText(activeElement.name)

    }

    override fun getItemCount(): Int {
        return mActiveElement.size
    }


}

