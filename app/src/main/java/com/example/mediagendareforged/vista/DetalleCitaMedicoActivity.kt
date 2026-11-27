package com.example.mediagendareforged.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediagendareforged.R
import com.example.mediagendareforged.presentador.clsDetalleCitaMedicoPresentador

class DetalleCitaMedicoActivity : AppCompatActivity() {

    private lateinit var presentador: clsDetalleCitaMedicoPresentador

    private lateinit var btnRegresar: ImageButton
    private lateinit var scrContenido: ScrollView
    private lateinit var txtPaciente: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtHora: TextView
    private lateinit var txtConsultorio: TextView
    private lateinit var txtUbicacion: TextView
    private lateinit var txtEstado: TextView
    private lateinit var txtVerPaciente: TextView
    private lateinit var navInicio: LinearLayout
    private lateinit var navPacientes: LinearLayout
    private lateinit var prgCargando: ProgressBar

    private var idCita = 0
    private var idPaciente = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cita_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        idCita = intent.getIntExtra("ID_CITA", 0)

        enlazarVistas()
        presentador = clsDetalleCitaMedicoPresentador(this)
        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navAgenda))
    }

    override fun onResume() {
        super.onResume()
        presentador.cargarDetalle(idCita)
    }

    private fun enlazarVistas() {
        btnRegresar = findViewById(R.id.btnRegresar)
        scrContenido = findViewById(R.id.scrContenido)
        txtPaciente = findViewById(R.id.txtPaciente)
        txtFecha = findViewById(R.id.txtFecha)
        txtHora = findViewById(R.id.txtHora)
        txtConsultorio = findViewById(R.id.txtConsultorio)
        txtUbicacion = findViewById(R.id.txtUbicacion)
        txtEstado = findViewById(R.id.txtEstado)
        txtVerPaciente = findViewById(R.id.txtVerPaciente)
        navInicio = findViewById(R.id.navInicio)
        navPacientes = findViewById(R.id.navPacientes)
        prgCargando = findViewById(R.id.prgCargando)
    }

    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }
        navInicio.setOnClickListener { clsNavegacion.abrirPanelMedico(this) }

        txtVerPaciente.setOnClickListener {
            if (idPaciente != 0) {
                val intent = Intent(this, InfoPacienteActivity::class.java)
                intent.putExtra("ID_PACIENTE", idPaciente)
                startActivity(intent)
            }
        }
        navPacientes.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, MisPacientesActivity::class.java) }
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
    }

    fun mostrarCita(idPaciente: Int, paciente: String, fecha: String, hora: String,
                    consultorio: String, ubicacion: String, estado: String) {
        this.idPaciente = idPaciente
        txtPaciente.text = paciente
        txtFecha.text = fecha
        txtHora.text = hora
        txtConsultorio.text = consultorio
        txtUbicacion.text = ubicacion
        txtUbicacion.visibility = if (ubicacion.isEmpty()) View.GONE else View.VISIBLE
        clsEstilos.pintarEstado(txtEstado, estado)
        scrContenido.visibility = View.VISIBLE
    }

    fun mostrarErrorYCerrar(mensaje: String) {
        mostrarAviso(mensaje)
        finish()
    }

    private fun mostrarAviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun abrirSinConexion() {
        clsNavegacion.abrirSinConexion(this)
    }

    fun abrirSesionExpirada() {
        clsNavegacion.abrirSesionExpirada(this)
    }
}