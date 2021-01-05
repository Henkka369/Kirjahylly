package com.example.kirjahylly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            launchActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        findViewById<Button>(R.id.signin).setOnClickListener {
            //val email = findViewById<EditText>(R.id.username).text.toString()
            //val password = findViewById<EditText>(R.id.password).text.toString()
            // TESTAUKSEN AJAKSI KIINTEÄT TUNNUKSET
            val email = "hoppi@hotmail.com"
            val password = "Savonia1"

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        launchActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Kirjautuminen epäonnistui",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }

        findViewById<TextView>(R.id.createUser).setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun launchActivity() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}