package com.example.Projetomobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Projetomobile.R

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        val nome = intent.getStringExtra("obra_nome") ?: "Nome Desconhecido"
        val autor = intent.getStringExtra("obra_autor") ?: "Autor Desconhecido"
        val descricao = intent.getStringExtra("obra_descricao") ?: "Sem descrição disponível"
        val imagemUrl = intent.getStringExtra("obra_imagem_url") ?: ""

        val imageView = findViewById<ImageView>(R.id.photo_image)
        val nomeTextView = findViewById<TextView>(R.id.photo_nome)
        val autorTextView = findViewById<TextView>(R.id.photo_autor)
        val descricaoTextView = findViewById<TextView>(R.id.photo_descricao)

        Glide.with(this).load(imagemUrl).into(imageView)
        nomeTextView.text = nome
        autorTextView.text = autor
        descricaoTextView.text = descricao
    }
}