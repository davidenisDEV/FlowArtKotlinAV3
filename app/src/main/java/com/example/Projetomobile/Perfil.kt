package com.example.Projetomobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class Perfil : AppCompatActivity() {

    private lateinit var nome_user: TextView
    private lateinit var email_user: TextView
    private lateinit var bt_sair: Button
    private lateinit var bt_qr: Button
    private val db = FirebaseFirestore.getInstance()
    private lateinit var usersDB:String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil) // Colocado antes de iniciar()

        iniciar()

        bt_sair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Finaliza a Activity atual para que o usuário não volte ao perfil ao pressionar "Voltar"
        }

        bt_qr.setOnClickListener {

            val intent = Intent(this, Qrcode::class.java)
            startActivity(intent)
            finish() // Finaliza a Activity atual para que o usuário não volte ao perfil ao pressionar "Voltar"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        // Obtém o e-mail do usuário autenticado
        val email = FirebaseAuth.getInstance().currentUser?.email ?: return // Caso o email seja null, retorna

        // Obtém o UID do usuário
        val usersDB = FirebaseAuth.getInstance().currentUser?.uid ?: return // Verifica se o UID é nulo e retorna caso seja

        // Referência ao documento do usuário no Firestore
        val documentReference = db.collection("users").document(usersDB)

        // Adiciona um listener para ouvir mudanças no documento
        documentReference.addSnapshotListener { documentSnapshot, error ->
            if (error != null) {
                // Trata o erro, se houver
                Log.w("Firestore", "Erro ao ouvir mudanças no documento", error)
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Atualiza os campos na interface com os dados do documento
                val nome = documentSnapshot.getString("nome")
                nome_user.text = nome ?: "Nome não disponível"
                email_user.text = email
            }
        }
    }



    private fun iniciar() {
        nome_user = findViewById(R.id.nome_user)
        email_user = findViewById(R.id.email_user)
        bt_sair = findViewById(R.id.bt_sair)
        bt_qr = findViewById(R.id.bt_qr)
    }
}
