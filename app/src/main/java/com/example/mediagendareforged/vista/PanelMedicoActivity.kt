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
import com.example.mediagendareforged.presentador.clsPanelMedicoPresentador
import android.content.Intent

class PanelMedicoActivity : AppCompatActivity() {

    private lateinit var presentador: clsPanelMedicoPresentador

    private lateinit var btnCerrarSesion: ImageButton
    private lateinit var txtNombreMedico: TextView
    private lateinit var txtEspecialidad: TextView
    private lateinit var txtProgramadas: TextView
    private lateinit var txtAtendidas: TextView
    private lateinit var lytSiguienteCita: LinearLayout
    private lateinit var txtCitaMes: TextView
    private lateinit var txtCitaDia: TextView
    private lateinit var txtCitaHora: TextView
    private lateinit var txtCitaPersona: TextView
    private lateinit var txtCitaDetalle: TextView
    private lateinit var txtCitaEstado: TextView
    private lateinit var txtSinCitas: TextView
    private lateinit var lytAccesoAgenda: LinearLayout
    private lateinit var lytAccesoPacientes: LinearLayout
    private lateinit var navAgenda: LinearLayout
    private lateinit var navPacientes: LinearLayout
    private lateinit var prgCargando: ProgressBar

    private var idSiguienteCita = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_panel_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsPanelMedicoPresentador(this)
        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navInicio))

        txtNombreMedico.text = intent.getStringExtra("NOMBRE") ?: ""

    }

    override fun onResume() {
        super.onResume()
        presentador.cargarPanel()
    }

    private fun enlazarVistas() {
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        txtNombreMedico = findViewById(R.id.txtNombreMedico)
        txtEspecialidad = findViewById(R.id.txtEspecialidad)
        txtProgramadas = findViewById(R.id.txtProgramadas)
        txtAtendidas = findViewById(R.id.txtAtendidas)
        lytSiguienteCita = findViewById(R.id.lytSiguienteCita)
        txtCitaMes = findViewById(R.id.txtCitaMes)
        txtCitaDia = findViewById(R.id.txtCitaDia)
        txtCitaHora = findViewById(R.id.txtCitaHora)
        txtCitaPersona = findViewById(R.id.txtCitaPersona)
        txtCitaDetalle = findViewById(R.id.txtCitaDetalle)
        txtCitaEstado = findViewById(R.id.txtCitaEstado)
        txtSinCitas = findViewById(R.id.txtSinCitas)
        lytAccesoAgenda = findViewById(R.id.lytAccesoAgenda)
        lytAccesoPacientes = findViewById(R.id.lytAccesoPacientes)
        navAgenda = findViewById(R.id.navAgenda)
        navPacientes = findViewById(R.id.navPacientes)
        prgCargando = findViewById(R.id.prgCargando)
    }

    private fun configurarListeners() {
        btnCerrarSesion.setOnClickListener { presentador.cerrarSesion() }

        lytSiguienteCita.setOnClickListener {
            if (idSiguienteCita != 0) {
                val intent = Intent(this, DetalleCitaMedicoActivity::class.java)
                intent.putExtra("ID_CITA", idSiguienteCita)
                startActivity(intent)
            }
        }
        lytAccesoAgenda.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, AgendaActivity::class.java) }
        navAgenda.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, AgendaActivity::class.java) }
        lytAccesoPacientes.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, MisPacientesActivity::class.java) }
        navPacientes.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, MisPacientesActivity::class.java) }
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
        btnCerrarSesion.isEnabled = !cargando
    }

    fun mostrarMedico(nombre: String, especialidad: String) {
        txtNombreMedico.text = nombre
        txtEspecialidad.text = especialidad
    }

    fun mostrarResumen(programadas: Int, atendidas: Int) {
        txtProgramadas.text = programadas.toString()
        txtAtendidas.text = atendidas.toString()
    }

    fun mostrarSiguienteCita(idCita: Int, mes: String, dia: String, hora: String,
                             paciente: String, consultorio: String, estado: String) {
        idSiguienteCita = idCita
        txtCitaMes.text = mes
        txtCitaDia.text = dia
        txtCitaHora.text = hora
        txtCitaPersona.text = paciente
        txtCitaDetalle.text = consultorio
        clsEstilos.pintarEstado(txtCitaEstado, estado)
        lytSiguienteCita.visibility = View.VISIBLE
        txtSinCitas.visibility = View.GONE
    }

    fun mostrarSinCitas() {
        idSiguienteCita = 0
        lytSiguienteCita.visibility = View.GONE
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