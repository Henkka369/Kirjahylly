package com.example.kirjahylly.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.MainActivity
import com.example.kirjahylly.R
import com.squareup.picasso.Picasso

class BookListAdapter(private val bookTitles: List<String>, private val bookImages: List<String>): RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.recyclerTextView)
        val imageView: ImageView = view.findViewById(R.id.recyclerImageView)
        val itemHolder: ConstraintLayout = view.findViewById(R.id.itemHolder)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = bookTitles[position]
        val img = viewHolder.imageView
        Picasso.get().load(bookImages[position]).into(img)

        viewHolder.itemHolder.setOnClickListener {
            AlertDialog.Builder(viewHolder.itemHolder.context)
                .setTitle("Lisää kirja")
                .setMessage("Haluatko lisätä kirjan kirjahyllyysi?")
                .setPositiveButton("Kyllä", DialogInterface.OnClickListener { dialog, which ->  Toast.makeText(viewHolder.itemHolder.context, "clicked $position", Toast.LENGTH_SHORT).show()})
                .setNegativeButton("Ei", null)
                .show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bookTitles.size

}