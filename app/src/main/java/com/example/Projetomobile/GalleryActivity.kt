package com.example.Projetomobile.ui.gallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Projetomobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Obra(
    val nome: String = "",
    val autor: String = "",
    val descricao: String = "",
    val imagem_url: String = ""
)

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryAdapter
    private val obrasList = mutableListOf<Obra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerView = findViewById(R.id.recycler_view_gallery)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        adapter = GalleryAdapter(obrasList)
        recyclerView.adapter = adapter

        carregarGaleriaDoUsuario()
    }

    private fun carregarGaleriaDoUsuario() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(userId).collection("galeria")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val obra = document.toObject(Obra::class.java)
                    obrasList.add(obra)
                }
                adapter.notifyDataSetChanged() // Atualizar o adaptador após carregar as obras
            }
            .addOnFailureListener { exception ->
                Log.e("Gallery", "Erro ao carregar galeria: ", exception)
            }
    }
}
