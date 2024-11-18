package com.example.Projetomobile

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Gemini : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gemini)

        // Configurar padding para o layout principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obter o valor escaneado passado via Intent
        val scannedValue = intent.getStringExtra("scanned_value")

        // Chamar a função IA com o valor escaneado
        IA(scannedValue)
    }

    // Função IA modificada para receber o texto escaneado
    public fun IA(scannedText: String?) {
        var textv = findViewById<TextView>(R.id.helloTextView)
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBP2U8adxEEuLxgqlELyETJVdyGr3P1yTA"
            )

        // Usar o valor escaneado no prompt
        val prompt = if (scannedText != null) {
            "fale sobre a obra $scannedText, o autor(apenas nome)+ data da obra, contexto(5 linhas) e resumo(10 linhas)"
        } else {
            "ALGO ERRADO"
        }

        MainScope().launch {
            val response = generativeModel.generateContent(prompt)
            textv.text = response.text
        }
    }
}
