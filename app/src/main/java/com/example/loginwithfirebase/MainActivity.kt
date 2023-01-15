package com.example.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // Cria variaveis dos campos:
    private lateinit var email: TextInputEditText
    private lateinit var senha: TextInputEditText
    private lateinit var logar: Button

    // Cria varaivel de autenticação:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicializar variaveis dos campos:
        email = findViewById(R.id.txt_Email)
        senha = findViewById(R.id.txt_Senha)
        logar = findViewById(R.id.btn_logar)

        // incializa variavel de autenticação:
        auth = Firebase.auth

        // click logar:
        logar.setOnClickListener {
            // pega texto dos campos:
            val emailS = email.text.toString().trim()
            val senhaS = senha.text.toString()

            if (emailS.isEmpty() || senhaS.isEmpty()){
                // Erro, há algum campo em branco
                Toast.makeText(this, "Há algum campo em branco!", Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(emailS, senhaS)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // logado com sucesso
                            // vai para outra tela
                            startActivity(Intent(this, Home::class.java))
                            finish()
                        }else{
                            // Aqui tem o tratamento de erro caso não seja logado com sucesso
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                // erro email mal formatado
                                Toast.makeText(this, "O endereço de e-mail está mal formatado!", Toast.LENGTH_LONG).show()
                            } catch (e: FirebaseAuthInvalidUserException){
                                // erro email nao ta cadastrado
                                Toast.makeText(this, "Endereço de e-mail não está cadastrado!!", Toast.LENGTH_LONG).show()
                            } catch (e: FirebaseAuthException){
                                // erro abrangente
                                Toast.makeText(this, "Erro ao relizar o login!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // obtem o dado do usuario autal
        val currentUser = auth.currentUser
        // verifica se há algum usuario logado
        // se estiver usuario conectdado (not null) vai para a tela home
        // caso contrario continua na tela de login
        if(currentUser != null){
            // está logado, vai pra tela home
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }
}