package com.example.Projetomobile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.material.snackbar.Snackbar

class Login : AppCompatActivity() {
    private lateinit var textTelaCadastro: TextView
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var botao_entrar: Button
    private lateinit var loading: ProgressBar
    private lateinit var google: ImageButton
    private lateinit var googleLogin: GoogleSignInClient
    private val msg = arrayOf("Preencha os campos", "Acesso realizado com sucesso")
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializando o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("158007984917-t58hrd6h6noev8adsnipmjm21hbe7ki9.apps.googleusercontent.com")  // Substitua com seu client ID
            .requestEmail()
            .build()

        googleLogin = GoogleSignIn.getClient(this, gso)

        startComponentes()

        // Configuração do layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navegar para a tela de cadastro
        textTelaCadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        // Ação do botão de login
        botao_entrar.setOnClickListener { v ->
            val email = edit_email.text.toString()
            val senha = edit_senha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(v, msg[0], Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            } else {
                autenticar()
            }
        }

        // Ação do botão de login com Google
        google.setOnClickListener {
            loginGoogle()
        }
    }

    // Função para login com Google
    private fun loginGoogle() {
        val intent = googleLogin.signInIntent
        abreActivity.launch(intent)
    }

    // Resultado da atividade de login com Google
    var abreActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken!!)
            } catch (exception: ApiException) {
                Toast.makeText(this, "Falha no login com Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para autenticação com Google no Firebase
    private fun loginComGoogle(token: String) {
        val credencial = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credencial).addOnCompleteListener(this) { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Autenticação ok", Toast.LENGTH_SHORT).show()
                abrirTelaPrincipal()
            } else {
                Toast.makeText(baseContext, "Erro na autenticação", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para autenticação com email e senha
    private fun autenticar() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loading.visibility = View.VISIBLE
                Handler().postDelayed({
                    abrirTelaPrincipal()
                }, 4000)
            } else {
                val snackbar = Snackbar.make(findViewById(R.id.main), "Erro ao fazer login", Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }
    }

    // Função para abrir a tela principal
    private fun abrirTelaPrincipal() {
        val intent = Intent(this, Perfil::class.java)
        startActivity(intent)
        finish()
    }

    // Função para inicializar componentes da tela
    private fun startComponentes() {
        textTelaCadastro = findViewById(R.id.text_cadastro)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        botao_entrar = findViewById(R.id.botao_entrar)
        loading = findViewById(R.id.loading)
        google = findViewById(R.id.google) // Inicializando o botão do Google
    }
}
