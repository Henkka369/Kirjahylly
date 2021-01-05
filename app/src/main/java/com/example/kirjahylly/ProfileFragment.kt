package com.example.kirjahylly

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.adapters.BookListAdapter
import com.example.kirjahylly.adapters.ShelfAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser

        if (user != null) {
            view.findViewById<TextView>(R.id.name).text = user.displayName
            view.findViewById<TextView>(R.id.email).text = user.email
        }

        val recycler: RecyclerView = view.findViewById(R.id.shelfList)
        recycler.layoutManager = LinearLayoutManager(context)
        addShelfs(recycler)

        /*view.findViewById<CardView>(R.id.luettu).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("shelf", it.toString())
            val bookListFragment = BookListFragment()
            bookListFragment.arguments = bundle
            findNavController().navigate(R.id.action_profileFragment_to_bookListFragment, bundle)
        }*/

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val dialog = AddShelfFragment()
        dialog.show(parentFragmentManager, "AddShelf")
    }

    private fun addShelfs(recycler: RecyclerView) {
        var shelfNames = hashMapOf<String, String>()

        val db = Firebase.firestore
        db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    if (data != null) {
                        for (item in data) {
                            if (item.value is HashMap<*,*>) {
                                shelfNames[item.key] = (item.value as HashMap<*, *>).size.toString()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
            .addOnCompleteListener {
                var names = mutableListOf<String>()
                var counts = mutableListOf<String>()
                for (value in shelfNames) {
                    names.add(value.key)
                    counts.add(value.value)
                }
                recycler.adapter = ShelfAdapter(names, counts)
            }
    }
}