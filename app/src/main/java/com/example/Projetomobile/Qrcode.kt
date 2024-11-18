package com.example.Projetomobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class Qrcode : AppCompatActivity() {
    private lateinit var scanQrBtn: Button
    private lateinit var scannedValueTv: TextView

    private val scannerLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            // Enviar o valor escaneado para a Activity Gemini
            val intent = Intent(this, Gemini::class.java)
            intent.putExtra("scanned_value", result.contents) // Passar o texto escaneado
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        scanQrBtn = findViewById(R.id.scan_bt)
        scannedValueTv = findViewById(R.id.scanned)


        registerUiListener()
    }

    private fun registerUiListener() {
        scanQrBtn.setOnClickListener {
            scannerLauncher.launch(
                ScanOptions().setPrompt("LENDO QR CODE")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            )
        }
    }
}
