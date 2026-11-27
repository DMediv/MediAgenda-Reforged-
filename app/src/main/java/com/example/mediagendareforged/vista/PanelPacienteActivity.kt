package com.example.mediagendareforged.vista

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediagendareforged.R
import com.example.mediagendareforged.presentador.clsPanelPacientePresentador

class PanelPacienteActivity : AppCompatActivity() {

    private lateinit var presentador: clsPanelPacientePresentador

    private lateinit var btnCerrarSesion: ImageButton
    private lateinit var txtSaludo: TextView
    private lateinit var lytProximaCita: LinearLayout
    private lateinit var txtCitaMes: TextView
    private lateinit var txtCitaDia: TextView
    private lateinit var txtCitaHora: TextView
    private lateinit var txtCitaPersona: TextView
    private lateinit var txtCitaDetalle: TextView
    private lateinit var txtCitaEstado: TextView
    private lateinit var txtSinCitas: TextView
    private lateinit var lytAccesoInfo: LinearLayout
    private lateinit var lytAccesoMisCitas: LinearLayout
    private lateinit var lytAccesoAgendar: LinearLayout
    private lateinit var lytAccesoHistorial: LinearLayout
    private lateinit var navMisCitas: LinearLayout
    private lateinit var navAgendar: LinearLayout
    private lateinit var navHistorial: LinearLayout
    private lateinit var navPerfil: LinearLayout
    private lateinit var prgCargando: ProgressBar

    private var idProximaCita = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_panel_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsPanelPacientePresentador(this)
        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navInicio))

        val nombre = intent.getStringExtra("NOMBRE") ?: ""
        if (nombre.isNotEmpty()) mostrarSaludo(nombre.split(" ").first())
    }

    override fun onResume() {
        super.onResume()
        presentador.cargarPanel()
    }

    private fun enlazarVistas() {
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        txtSaludo = findViewById(R.id.txtSaludo)
        lytProximaCita = findViewById(R.id.lytProximaCita)
        txtCitaMes = findViewById(R.id.txtCitaMes)
        txtCitaDia = findViewById(R.id.txtCitaDia)
        txtCitaHora = findViewById(R.id.txtCitaHora)
        txtCitaPersona = findViewById(R.id.txtCitaPersona)
        txtCitaDetalle = findViewById(R.id.txtCitaDetalle)
        txtCitaEstado = findViewById(R.id.txtCitaEstado)
        txtSinCitas = findViewById(R.id.txtSinCitas)
        lytAccesoInfo = findViewById(R.id.lytAccesoInfo)
        lytAccesoMisCitas = findViewById(R.id.lytAccesoMisCitas)
        lytAccesoAgendar = findViewById(R.id.lytAccesoAgendar)
        lytAccesoHistorial = findViewById(R.id.lytAccesoHistorial)
        navMisCitas = findViewById(R.id.navMisCitas)
        navAgendar = findViewById(R.id.navAgendar)
        navHistorial = findViewById(R.id.navHistorial)
        navPerfil = findViewById(R.id.navPerfil)
        prgCargando = findViewById(R.id.prgCargando)
    }

    private fun configurarListeners() {
        btnCerrarSesion.setOnClickListener { presentador.cerrarSesion() }

        lytProximaCita.setOnClickListener { mostrarAviso("Detalle de cita: próximamente") }
        lytAccesoInfo.setOnClickListener { mostrarAviso("Información personal: próximamente") }
        navPerfil.setOnClickListener { mostrarAviso("Información personal: próximamente") }
        lytAccesoMisCitas.setOnClickListener { mostrarAviso("Mis citas: próximamente") }
        navMisCitas.setOnClickListener { mostrarAviso("Mis citas: próximamente") }
        lytAccesoAgendar.setOnClickListener { mostrarAviso("Agendar cita: próximamente") }
        navAgendar.setOnClickListener { mostrarAviso("Agendar cita: próximamente") }
        lytAccesoHistorial.setOnClickListener { mostrarAviso("Historial: próximamente") }
        navHistorial.setOnClickListener { mostrarAviso("Historial: próximamente") }
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
        btnCerrarSesion.isEnabled = !cargando
    }

    fun mostrarSaludo(nombre: String) {
        txtSaludo.text = getString(R.string.panel_saludo, nombre)
    }

    fun mostrarProximaCita(idCita: Int, mes: String, dia: String, hora: String,
                           medico: String, detalle: String, estado: String) {
        idProximaCita = idCita
        txtCitaMes.text = mes
        txtCitaDia.text = dia
        txtCitaHora.text = hora
        txtCitaPersona.text = medico
        txtCitaDetalle.text = detalle
        clsEstilos.pintarEstado(txtCitaEstado, estado)
        lytProximaCita.visibility = View.VISIBLE
        txtSinCitas.visibility = View.GONE
    }

    fun mostrarSinCitas() {
        idProximaCita = 0
        lytProximaCita.visibility = View.GONE
        txtSinCitas.visibility = View.VISIBLE
    }

    fun mostrarAviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun abrirSinConexion() {
        clsNavegacion.abrirSinConexion(this)
    }

    fun abrirSesionExpirada() {
        clsNavegacion.abrirSesionExpirada(this)
    }
    fun abrirLogin() {
        clsNavegacion.abrirLogin(this)
    }
}