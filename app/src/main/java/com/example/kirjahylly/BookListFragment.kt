package com.example.kirjahylly

import android.os.Bundle
import android.util.Xml
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.adapters.BookListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.xml.sax.SAXException
import org.xmlpull.v1.XmlSerializer
import java.io.IOException
import java.net.URL
import java.net.URLConnection
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class BookListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val recycler: RecyclerView = view.findViewById(R.id.bookList)
        val items = listOf<String>("eka", "toka", "kolmas")
        recycler.adapter = BookListAdapter(items)*/
        val url = URL("https://www.goodreads.com/search.xml?key=qnlK0k941lTJFUqoDvJyA&q=Harry+Potter")
        UrlLoader(url, view).execute()

        view.findViewById<FloatingActionButton>(R.id.addBookFab).setOnClickListener {
            findNavController().navigate(R.id.action_bookListFragment_to_addBookFragment)
        }
    }
}