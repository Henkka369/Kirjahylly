package com.example.kirjahylly

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddShelfFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val db = Firebase.firestore

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_add_shelf, null))
                // Add action buttons
                .setPositiveButton("Hyväksy",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Koodi, joka vie hyllyn tietokantaan
                        val shelfName = getDialog()?.findViewById<EditText>(R.id.editTextTextPersonName)?.text.toString()
                        val newBook = hashMapOf<Any?, Any?>(
                            "Name" to "Tuntematon Sotilas",
                            "Author" to "Väinö Linna"
                        )
                        val newShelf = hashMapOf<Any?, Any?>(
                            "Kirja" to newBook
                        )
                        val newCollection = hashMapOf<Any?, Any?>(
                            shelfName to newShelf
                        )

                        db.collection("users").document("mG5qEcDsNNVIo3hm71SnorAyBTP2")
                            .set(newCollection, SetOptions.merge())
                            .addOnSuccessListener { Log.d(TAG, "Kirjahyllyn lisäys onnistui!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    })
                .setNegativeButton("Peruuta",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}