package com.example.kirjahylly

import android.app.ActionBar
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.adapters.BookListAdapter
import com.example.kirjahylly.adapters.BookShelfAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.net.URL

class BookListFragment : Fragment() {

    private var shelf = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shelf = arguments?.getString("shelf").toString()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookTitles = mutableListOf<String>()
        val bookImages = mutableListOf<String>()

        val db = Firebase.firestore
        db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    if (data != null) {
                        for (item in data) {
                            if (item.key == shelf) {
                                val books = (item.value as HashMap<*, *>)
                                for (book in books) {
                                    bookTitles.add((book.value as HashMap<*,*>)["title"].toString())
                                    bookImages.add((book.value as HashMap<*,*>)["img_url"].toString())
                                }
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .addOnCompleteListener {
                val recycler: RecyclerView = view.findViewById(R.id.shelfBooks)
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.adapter = BookShelfAdapter(bookTitles, bookImages)
            }

        view.findViewById<FloatingActionButton>(R.id.addBookFab).setOnClickListener {
            findNavController().navigate(R.id.action_bookListFragment_to_addBookFragment)
        }
    }
}