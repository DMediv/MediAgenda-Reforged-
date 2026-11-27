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
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediagendareforged.R
import com.example.mediagendareforged.presentador.clsInfoPacientePresentador

class InfoPacienteActivity : AppCompatActivity() {

    private lateinit var presentador: clsInfoPacientePresentador

    private lateinit var btnRegresar: ImageButton
    private lateinit var scrContenido: ScrollView
    private lateinit var txtIniciales: TextView
    private lateinit var txtNombre: TextView
    private lateinit var lytDatosPersonales: LinearLayout
    private lateinit var lytTarjetaHistorial: LinearLayout
    private lateinit var lytHistorial: LinearLayout
    private lateinit var btnVerHistorial: AppCompatButton
    private lateinit var prgCargando: ProgressBar
    private lateinit var navInicio: LinearLayout
    private lateinit var navAgenda: LinearLayout

    private var idPaciente = 0
    private var nombrePaciente = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        idPaciente = intent.getIntExtra("ID_PACIENTE", 0)

        enlazarVistas()
        presentador = clsInfoPacientePresentador(this)
        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navPacientes))
    }

    override fun onResume() {
        super.onResume()
        presentador.cargarInfo(idPaciente)
    }

    private fun enlazarVistas() {
        btnRegresar = findViewById(R.id.btnRegresar)
        scrContenido = findViewById(R.id.scrContenido)
        txtIniciales = findViewById(R.id.txtIniciales)
        txtNombre = findViewById(R.id.txtNombre)
        lytDatosPersonales = findViewById(R.id.lytDatosPersonales)
        lytTarjetaHistorial = findViewById(R.id.lytTarjetaHistorial)
        lytHistorial = findViewById(R.id.lytHistorial)
        btnVerHistorial = findViewById(R.id.btnVerHistorial)
        prgCargando = findViewById(R.id.prgCargando)
        navInicio = findViewById(R.id.navInicio)
        navAgenda = findViewById(R.id.navAgenda)
    }

    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }
        navInicio.setOnClickListener { clsNavegacion.abrirPanelMedico(this) }
        navAgenda.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, AgendaActivity::class.java) }
        btnVerHistorial.setOnClickListener {
            val intent = Intent(this, HistorialPacienteActivity::class.java)
            intent.putExtra("ID_PACIENTE", idPaciente)
            intent.putExtra("NOMBRE", nombrePaciente)
            startActivity(intent)
        }
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
    }

    fun mostrarPaciente(nombre: String, fechaNacimiento: String, genero: String,
                        telefono: String, direccion: String, correo: String) {
        nombrePaciente = nombre
        txtIniciales.text = clsEstilos.iniciales(nombre)
        txtNombre.text = nombre

        lytDatosPersonales.removeAllViews()
        agregarDato(lytDatosPersonales, R.string.info_fecha_nacimiento, fechaNacimiento)
        agregarDato(lytDatosPersonales, R.string.info_genero, genero)
        agregarDato(lytDatosPersonales, R.string.info_telefono, telefono)
        agregarDato(lytDatosPersonales, R.string.info_direccion, direccion)
        agregarDato(lytDatosPersonales, R.string.info_correo, correo)

        scrContenido.visibility = View.VISIBLE
    }

    fun mostrarHistorial(tipoSangre: String, peso: String, estatura: String, alergias: String,
                         enfermedades: String, medicamentos: String, cirugias: String,
                         antecedentes: String, tabaquismo: String, alcohol: String) {
        lytHistorial.removeAllViews()
        agregarDato(lytHistorial, R.string.info_tipo_sangre, tipoSangre)
        agregarDato(lytHistorial, R.string.info_peso, peso)
        agregarDato(lytHistorial, R.string.info_estatura, estatura)
        agregarDato(lytHistorial, R.string.info_alergias, alergias)
        agregarDato(lytHistorial, R.string.info_enfermedades, enfermedades)
        agregarDato(lytHistorial, R.string.info_medicamentos, medicamentos)
        agregarDato(lytHistorial, R.string.info_cirugias, cirugias)
        agregarDato(lytHistorial, R.string.info_antecedentes, antecedentes)
        agregarDato(lytHistorial, R.string.info_tabaquismo, tabaquismo)
        agregarDato(lytHistorial, R.string.info_alcohol, alcohol)
        lytTarjetaHistorial.visibility = View.VISIBLE
    }

    fun ocultarHistorial() {
        lytTarjetaHistorial.visibility = View.GONE
    }

    private fun agregarDato(contenedor: LinearLayout, etiqueta: Int, valor: String) {
        val fila = layoutInflater.inflate(R.layout.fila_dato, contenedor, false)
        fila.findViewById<TextView>(R.id.txtEtiquetaDato).setText(etiqueta)
        fila.findViewById<TextView>(R.id.txtValorDato).text = valor
        if (contenedor.childCount == 0) {
            fila.findViewById<View>(R.id.vDivisor).visibility = View.GONE
        }
        contenedor.addView(fila)
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