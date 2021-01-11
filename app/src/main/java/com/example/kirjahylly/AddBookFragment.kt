package com.example.kirjahylly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.net.URL

class AddBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.search_button).setOnClickListener {
            val userInput = view.findViewById<EditText>(R.id.search_box).text.toString()
            val dev_key = getText(R.string.dev_key) // Goodreadsin Developer Key
            val url = URL("https://www.goodreads.com/search.xml?key=$dev_key&q=$userInput")
            UrlLoader(url, view).execute()
        }
    }
}