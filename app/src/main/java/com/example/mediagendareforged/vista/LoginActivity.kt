package com.example.mediagendareforged.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediagendareforged.R
import com.example.mediagendareforged.presentador.clsLoginPresentador
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var presentador: clsLoginPresentador

    private lateinit var tilUsuario: TextInputLayout
    private lateinit var txtUsuario: TextInputEditText
    private lateinit var tilContrasena: TextInputLayout
    private lateinit var txtContrasena: TextInputEditText
    private lateinit var lytAvisoError: LinearLayout
    private lateinit var txtMensajeError: TextView
    private lateinit var btnAcceder: AppCompatButton
    private lateinit var prgCargando: ProgressBar
    private lateinit var txtOlvideContrasena: TextView
    private lateinit var txtCrearCuenta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsLoginPresentador(this)
        configurarListeners()
    }

    private fun enlazarVistas() {
        tilUsuario = findViewById(R.id.tilUsuario)
        txtUsuario = findViewById(R.id.txtUsuario)
        tilContrasena = findViewById(R.id.tilContrasena)
        txtContrasena = findViewById(R.id.txtContrasena)
        lytAvisoError = findViewById(R.id.lytAvisoError)
        txtMensajeError = findViewById(R.id.txtMensajeError)
        btnAcceder = findViewById(R.id.btnAcceder)
        prgCargando = findViewById(R.id.prgCargando)
        txtOlvideContrasena = findViewById(R.id.txtOlvideContrasena)
        txtCrearCuenta = findViewById(R.id.txtCrearCuenta)
    }

    private fun configurarListeners() {
        btnAcceder.setOnClickListener {
            presentador.iniciarSesion(
                txtUsuario.text.toString(),
                txtContrasena.text.toString()
            )
        }
        // Se conectan cuando existan sus pantallas (A3 y A7)
        txtCrearCuenta.setOnClickListener { mostrarAviso("Registro: próximamente") }
        txtOlvideContrasena.setOnClickListener { mostrarAviso("Recuperar contraseña: próximamente") }
    }


    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
        btnAcceder.isEnabled = !cargando
    }

    fun mostrarErrorUsuario(mensaje: String?) {
        tilUsuario.error = mensaje
    }

    fun mostrarErrorContrasena(mensaje: String?) {
        tilContrasena.error = mensaje
    }

    fun mostrarError(mensaje: String) {
        txtMensajeError.text = mensaje
        lytAvisoError.visibility = View.VISIBLE
    }

    fun ocultarError() {
        lytAvisoError.visibility = View.GONE
    }

    fun abrirPanelPaciente(nombre: String) {
        abrirPanel(Intent(this, PanelPacienteActivity::class.java), nombre)
    }

    fun abrirPanelMedico(nombre: String) {
        abrirPanel(Intent(this, PanelMedicoActivity::class.java), nombre)
    }

    private fun abrirPanel(intent: Intent, nombre: String) {
        intent.putExtra("NOMBRE", nombre)
        startActivity(intent)
        finish()
    }

    private fun mostrarAviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}