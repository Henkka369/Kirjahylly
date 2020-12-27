package com.example.kirjahylly

import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kirjahylly.adapters.BookListAdapter
import org.w3c.dom.Document
import java.net.URL
import java.net.URLConnection
import javax.xml.parsers.DocumentBuilderFactory

class UrlLoader(private val url: URL, private val view: View): AsyncTask<Void, Void, Document>() {
    override fun doInBackground(vararg params: Void?): Document {
        val urlConnection: URLConnection = url.openConnection()
        urlConnection.connect()

        val inputStream = urlConnection.getInputStream()
        val builderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = builderFactory.newDocumentBuilder()

        return docBuilder.parse(inputStream)
    }

    override fun onPostExecute(result: Document?) {
        super.onPostExecute(result)
        result?.documentElement?.normalize()
        if (result != null) {
            val titleList = result.getElementsByTagName("title")
            val imageList = result.getElementsByTagName("image_url")
            var bookTitles = mutableListOf<String>()
            var bookImages = mutableListOf<String>()
            for (i in 0 until titleList.length) {
                bookTitles.add(titleList.item(i).firstChild.nodeValue)
                bookImages.add(imageList.item(i).firstChild.nodeValue)
            }
            val recycler: RecyclerView = view.findViewById(R.id.bookList)
            recycler.adapter = BookListAdapter(bookTitles, bookImages)
        }
    }
}