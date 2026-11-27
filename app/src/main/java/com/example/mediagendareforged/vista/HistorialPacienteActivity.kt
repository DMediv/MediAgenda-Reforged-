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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.adaptador.clsCitasAdaptador
import com.example.mediagendareforged.modelo.dataCita
import com.example.mediagendareforged.presentador.clsHistorialPacientePresentador

class HistorialPacienteActivity : AppCompatActivity() {

    private lateinit var presentador: clsHistorialPacientePresentador
    private val adaptador = clsCitasAdaptador()

    private lateinit var btnRegresar: ImageButton
    private lateinit var txtNombrePaciente: TextView
    private lateinit var txtPestanaConmigo: TextView
    private lateinit var txtPestanaOtras: TextView
    private lateinit var rcvCitas: RecyclerView
    private lateinit var txtVacio: TextView
    private lateinit var prgCargando: ProgressBar
    private lateinit var navInicio: LinearLayout
    private lateinit var navAgenda: LinearLayout
    private lateinit var navPacientes: LinearLayout

    private var idPaciente = 0
    private var ambitoActual = clsHistorialPacientePresentador.CONMIGO
    private var recargarAlVolver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        idPaciente = intent.getIntExtra("ID_PACIENTE", 0)

        enlazarVistas()
        presentador = clsHistorialPacientePresentador(this)
        rcvCitas.layoutManager = LinearLayoutManager(this)
        rcvCitas.adapter = adaptador
        txtNombrePaciente.text = intent.getStringExtra("NOMBRE") ?: ""

        configurarListeners()
        clsEstilos.activarDestino(navPacientes)

        seleccionarPestana(clsHistorialPacientePresentador.CONMIGO)
    }

    override fun onResume() {
        super.onResume()
        if (recargarAlVolver) {
            recargarAlVolver = false
            presentador.cargarCitas(idPaciente, ambitoActual)
        }
    }

    private fun enlazarVistas() {
        btnRegresar = findViewById(R.id.btnRegresar)
        txtNombrePaciente = findViewById(R.id.txtNombrePaciente)
        txtPestanaConmigo = findViewById(R.id.txtPestanaConmigo)
        txtPestanaOtras = findViewById(R.id.txtPestanaOtras)
        rcvCitas = findViewById(R.id.rcvCitas)
        txtVacio = findViewById(R.id.txtVacio)
        prgCargando = findViewById(R.id.prgCargando)
        navInicio = findViewById(R.id.navInicio)
        navAgenda = findViewById(R.id.navAgenda)
        navPacientes = findViewById(R.id.navPacientes)
    }

    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }
        txtPestanaConmigo.setOnClickListener { seleccionarPestana(clsHistorialPacientePresentador.CONMIGO) }
        txtPestanaOtras.setOnClickListener { seleccionarPestana(clsHistorialPacientePresentador.OTRAS) }
        navInicio.setOnClickListener { clsNavegacion.abrirPanelMedico(this) }
        navAgenda.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, AgendaActivity::class.java) }
        navPacientes.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, MisPacientesActivity::class.java) }
    }

    private fun seleccionarPestana(ambito: String) {
        ambitoActual = ambito
        txtPestanaConmigo.isSelected = ambito == clsHistorialPacientePresentador.CONMIGO
        txtPestanaOtras.isSelected = ambito == clsHistorialPacientePresentador.OTRAS
        presentador.cargarCitas(idPaciente, ambito)
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
        if (cargando) {
            rcvCitas.visibility = View.GONE
            txtVacio.visibility = View.GONE
        }
    }

    fun mostrarCitas(citas: List<dataCita>) {
        adaptador.actualizar(citas)
        rcvCitas.scrollToPosition(0)
        rcvCitas.visibility = View.VISIBLE
        txtVacio.visibility = View.GONE
    }

    fun mostrarVacio(mensaje: String) {
        adaptador.actualizar(emptyList())
        rcvCitas.visibility = View.GONE
        txtVacio.text = mensaje
        txtVacio.visibility = View.VISIBLE
    }

    fun mostrarErrorYCerrar(mensaje: String) {
        mostrarAviso(mensaje)
        finish()
    }

    private fun mostrarAviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun abrirSinConexion() {
        recargarAlVolver = true
        clsNavegacion.abrirSinConexion(this)
    }

    fun abrirSesionExpirada() {
        clsNavegacion.abrirSesionExpirada(this)
    }
}