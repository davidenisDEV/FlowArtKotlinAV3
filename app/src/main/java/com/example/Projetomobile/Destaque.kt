package com.example.Projetomobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.Projetomobile.R

class Destaque : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destaque)

        // Configurando o ViewPager2
        val viewPager: ViewPager2 = findViewById(R.id.view_pager_destaque)

        // Lista de imagens de destaque (substitua pelos IDs de imagens reais)
        val destaqueImages = listOf(
            R.drawable.art1,
            R.drawable.art2,
            R.drawable.art3
        )

        // Configurando o adaptador
        val adapter = DestaqueAdapter(destaqueImages)
        viewPager.adapter = adapter
    }
}
