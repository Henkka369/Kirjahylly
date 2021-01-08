package com.example.kirjahylly.adapters

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.util.Log
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
                .setPositiveButton("Kyllä", DialogInterface.OnClickListener { dialog, which ->  addBook(bookTitles[position], bookImages[position], viewHolder)})
                .setNegativeButton("Ei", null)
                .show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bookTitles.size

    private fun addBook(title: String, url: String, viewHolder: ViewHolder) {
        val db = Firebase.firestore
        var books = HashMap<Any, Any>()

        var activeShelf = viewHolder.itemView.context.openFileInput("activeShelf").bufferedReader().useLines { lines ->
            lines.fold("") { shelf, name ->
                "$shelf$name"
            }
        }

        db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    if (data != null) {
                        val shelf = data[activeShelf] as HashMap<*, *>
                        for (book in shelf) {
                            books[book.key] = book.value
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
            .addOnCompleteListener {
                books[title] = mapOf("title" to title, "img_url" to url)
                db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
                    .update(mapOf(
                        activeShelf to books
                    ))
            }
    }
}