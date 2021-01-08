package com.example.kirjahylly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        findViewById<Button>(R.id.signin).setOnClickListener {
            val name = findViewById<EditText>(R.id.newName).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        setUser(name)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Käyttäjän luominen epäonnistui",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun setUser(name: String) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "User profile updated.")
                }
                finish()
            }

        val db = Firebase.firestore

        val seed = mapOf<Any, Any>(
            "Haluan lukea" to mapOf<Any, Any>(
                "seed" to mapOf<Any, Any>(
                    "title" to "seed"
                )
            ),
            "Luettu" to mapOf<Any, Any>(
                "seed" to mapOf<Any, Any>(
                    "title" to "seed"
                )
            )
        )

        db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .set(seed)
            .addOnSuccessListener { Log.d("TAG", "Käyttäjän hyllyt lisätty") }
            .addOnFailureListener { e -> Log.w("TAG", "Hyllyjen lisäys epäonnistui", e) }
    }
}