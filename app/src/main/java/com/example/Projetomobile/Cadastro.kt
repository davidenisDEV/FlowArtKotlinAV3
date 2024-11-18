package com.example.Projetomobile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {
    private lateinit var cadastro_nome: EditText
    private lateinit var cadastro_email: EditText
    private lateinit var cadastro_senha: EditText
    private lateinit var bt_cadastro: Button
    private val msg = arrayOf("Preencha os campos", "Cadastrado com sucesso")
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startComponentes()

        bt_cadastro.setOnClickListener { v ->
            val nome = cadastro_nome.text.toString()
            val email = cadastro_email.text.toString()
            val senha = cadastro_senha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(v, msg[0], Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(Color.WHITE)
                snackbar.setTextColor(Color.BLACK)
                snackbar.show()
            } else {
                cadastrarUsuario(v)
            }
        }
    }

    private fun startComponentes() {
        cadastro_nome = findViewById(R.id.cadastro_nome)
        cadastro_email = findViewById(R.id.cadastro_email)
        cadastro_senha = findViewById(R.id.cadastro_senha)
        bt_cadastro = findViewById(R.id.bt_cadastro)
    }

    private fun cadastrarUsuario(v: android.view.View) {
        val email = cadastro_email.text.toString()
        val senha = cadastro_senha.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snackbar = Snackbar.make(v, msg[1], Snackbar.LENGTH_SHORT)
                    snackbar.view.setBackgroundColor(Color.GREEN)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()


                    salvarNoFirestore()
                } else {

                    val error = when (val exception = task.exception) {
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter no mínimo 6 caracteres"
                        is FirebaseAuthUserCollisionException -> "Esta conta já está cadastrada"
                        is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
                        else -> "Erro ao cadastrar: ${exception?.message}"
                    }

                    val snackbar = Snackbar.make(v, error, Snackbar.LENGTH_LONG)
                    snackbar.view.setBackgroundColor(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                }
            }
    }


    private fun salvarNoFirestore() {
        val nome = cadastro_nome.text.toString()
        val db = FirebaseFirestore.getInstance()


        val userMap = mutableMapOf<String, Any>()
        userMap["nome"] = nome


        userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val documentReference: DocumentReference = db.collection("users").document(userID)
        documentReference.set(userMap)
            .addOnSuccessListener {
                Log.d("bd sucesso", "Usuário salvo com sucesso no bd")
            }
            .addOnFailureListener { e ->
                Log.e("erro no bd", "Erro ao salvar usuário no bd", e)
            }
    }
}
