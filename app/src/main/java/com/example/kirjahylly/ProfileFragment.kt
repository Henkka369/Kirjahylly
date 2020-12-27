package com.example.kirjahylly

import android.os.Bundle
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

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

        view.findViewById<CardView>(R.id.luettu).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_bookListFragment)
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            openDialog()
        }
    }


    private fun openDialog() {
        val dialog = AddShelfFragment()
        dialog.show(parentFragmentManager, "AddShelf")
    }
}