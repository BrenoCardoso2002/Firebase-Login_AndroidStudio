package com.example.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {

    // Cria variaveis dos campos:
    private lateinit var logout: Button

    // Cria varaivel de autenticação:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // inicializar variaveis dos campos:
        logout = findViewById(R.id.bt_logout)

        // incializa variavel de autenticação:
        auth = Firebase.auth

        logout.setOnClickListener {
            // realiza o logout do usuario
            auth.signOut()
            // vai para outra tela
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}