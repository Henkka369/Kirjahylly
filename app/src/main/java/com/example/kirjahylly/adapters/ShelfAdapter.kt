package com.example.kirjahylly.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.BookListFragment
import com.example.kirjahylly.R

class ShelfAdapter(private val names: List<String>, private val counts: List<String>): RecyclerView.Adapter<ShelfAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView1: TextView = view.findViewById(R.id.shelfName)
        val textView2: TextView = view.findViewById(R.id.bookCount)
        val itemHolder: CardView = view.findViewById(R.id.card_item)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_shelf, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView1.text = names[position]
        viewHolder.textView2.text = counts[position]

        viewHolder.itemHolder.setOnClickListener {
            //Toast.makeText(viewHolder.itemHolder.context, "clicked $position", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString("shelf", names[position])
            val bookListFragment = BookListFragment()
            bookListFragment.arguments = bundle
            viewHolder.itemView.findNavController().navigate(R.id.action_profileFragment_to_bookListFragment, bundle)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return names.size
    }
}