package com.example.Projetomobile.ui.destaque

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.Projetomobile.R
import com.google.firebase.firestore.FirebaseFirestore

data class Obra(
    val nome: String = "",
    val autor: String = "",
    val descricao: String = "",
    val imagem_url: String = ""
)

class Destaque : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: DestaqueAdapter
    private val obrasList = mutableListOf<Obra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destaque)

        // Configurar ViewPager2
        viewPager = findViewById(R.id.view_pager_destaque)

        adapter = DestaqueAdapter(obrasList)
        viewPager.adapter = adapter

        // Buscar as imagens do Firestore
        fetchObrasFromFirestore()
    }

    private fun fetchObrasFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val obra = document.toObject(Obra::class.java)
                    obrasList.add(obra)
                }
                adapter.notifyDataSetChanged() // Atualizar o carrossel após carregar os dados
            }
            .addOnFailureListener { exception ->
                Log.e("Destaque", "Erro ao buscar obras no Firestore", exception)
            }
    }
}
