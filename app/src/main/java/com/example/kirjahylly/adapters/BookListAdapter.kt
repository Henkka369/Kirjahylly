package com.example.kirjahylly.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.R
import com.squareup.picasso.Picasso

class BookListAdapter(private val bookTitles: List<String>, private val bookImages: List<String>): RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.recyclerTextView)
            imageView = view.findViewById(R.id.recyclerImageView)
        }
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bookTitles.size

}