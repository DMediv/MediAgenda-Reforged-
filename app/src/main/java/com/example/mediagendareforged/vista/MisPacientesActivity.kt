package com.example.mediagendareforged.vista

import android.content.Intent
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
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.adaptador.clsPacientesAdaptador
import com.example.mediagendareforged.modelo.dataPacienteResumen
import com.example.mediagendareforged.presentador.clsMisPacientesPresentador
import com.google.android.material.textfield.TextInputEditText

class MisPacientesActivity : AppCompatActivity() {

    private lateinit var presentador: clsMisPacientesPresentador
    private lateinit var adaptador: clsPacientesAdaptador

    private lateinit var btnRegresar: ImageButton
    private lateinit var txtBuscar: TextInputEditText
    private lateinit var rcvPacientes: RecyclerView
    private lateinit var txtVacio: TextView
    private lateinit var prgCargando: ProgressBar
    private lateinit var navInicio: LinearLayout
    private lateinit var navAgenda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsMisPacientesPresentador(this)

        adaptador = clsPacientesAdaptador { idPaciente -> abrirInfoPaciente(idPaciente) }
        rcvPacientes.layoutManager = LinearLayoutManager(this)
        rcvPacientes.adapter = adaptador

        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navPacientes))
    }

    override fun onResume() {
        super.onResume()
        presentador.cargarPacientes()
    }

    private fun enlazarVistas() {
        btnRegresar = findViewById(R.id.btnRegresar)
        txtBuscar = findViewById(R.id.txtBuscar)
        rcvPacientes = findViewById(R.id.rcvPacientes)
        txtVacio = findViewById(R.id.txtVacio)
        prgCargando = findViewById(R.id.prgCargando)
        navInicio = findViewById(R.id.navInicio)
        navAgenda = findViewById(R.id.navAgenda)
    }

    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }
        txtBuscar.doAfterTextChanged { texto -> presentador.filtrar(texto?.toString() ?: "") }
        navInicio.setOnClickListener { clsNavegacion.abrirPanelMedico(this) }
        navAgenda.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, AgendaActivity::class.java) }
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
    }

    fun mostrarPacientes(pacientes: List<dataPacienteResumen>) {
        adaptador.actualizar(pacientes)
        rcvPacientes.visibility = View.VISIBLE
        txtVacio.visibility = View.GONE
    }

    fun mostrarSinPacientes() {
        mostrarVacio(getString(R.string.pacientes_sin_pacientes))
    }

    fun mostrarSinResultados() {
        mostrarVacio(getString(R.string.pacientes_sin_resultados))
    }

    private fun mostrarVacio(mensaje: String) {
        adaptador.actualizar(emptyList())
        rcvPacientes.visibility = View.GONE
        txtVacio.text = mensaje
        txtVacio.visibility = View.VISIBLE
    }
    private fun abrirInfoPaciente(idPaciente: Int) {
        val intent = Intent(this, InfoPacienteActivity::class.java)
        intent.putExtra("ID_PACIENTE", idPaciente)
        startActivity(intent)
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
}