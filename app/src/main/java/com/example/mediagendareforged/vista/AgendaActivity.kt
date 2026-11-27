package com.example.mediagendareforged.vista

import android.content.Intent
import android.content.res.ColorStateList
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
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.adaptador.clsCalendarioAdaptador
import com.example.mediagendareforged.modelo.dataCita
import com.example.mediagendareforged.modelo.dataDiaCalendario
import com.example.mediagendareforged.presentador.clsAgendaPresentador

class AgendaActivity : AppCompatActivity() {

    private lateinit var presentador: clsAgendaPresentador
    private lateinit var adaptador: clsCalendarioAdaptador

    private lateinit var btnRegresar: ImageButton
    private lateinit var btnMesAnterior: ImageButton
    private lateinit var btnMesSiguiente: ImageButton
    private lateinit var txtMes: TextView
    private lateinit var rcvCalendario: RecyclerView
    private lateinit var txtTituloCitas: TextView
    private lateinit var lytCitasDia: LinearLayout
    private lateinit var txtSinCitasDia: TextView
    private lateinit var prgCitas: ProgressBar
    private lateinit var navInicio: LinearLayout
    private lateinit var navPacientes: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agenda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsAgendaPresentador(this)

        adaptador = clsCalendarioAdaptador { fecha -> presentador.seleccionarDia(fecha) }
        rcvCalendario.layoutManager = GridLayoutManager(this, 7)   // 7 columnas
        rcvCalendario.adapter = adaptador

        configurarListeners()
        clsEstilos.activarDestino(findViewById(R.id.navAgenda))
    }

    override fun onResume() {
        super.onResume()
        presentador.recargar()
    }

    private fun enlazarVistas() {
        btnRegresar = findViewById(R.id.btnRegresar)
        btnMesAnterior = findViewById(R.id.btnMesAnterior)
        btnMesSiguiente = findViewById(R.id.btnMesSiguiente)
        txtMes = findViewById(R.id.txtMes)
        rcvCalendario = findViewById(R.id.rcvCalendario)
        txtTituloCitas = findViewById(R.id.txtTituloCitas)
        lytCitasDia = findViewById(R.id.lytCitasDia)
        txtSinCitasDia = findViewById(R.id.txtSinCitasDia)
        prgCitas = findViewById(R.id.prgCitas)
        navInicio = findViewById(R.id.navInicio)
        navPacientes = findViewById(R.id.navPacientes)
    }

    private fun configurarListeners() {
        btnRegresar.setOnClickListener { finish() }
        btnMesAnterior.setOnClickListener { presentador.mesAnterior() }
        btnMesSiguiente.setOnClickListener { presentador.mesSiguiente() }
        navInicio.setOnClickListener { clsNavegacion.abrirPanelMedico(this) }
        navPacientes.setOnClickListener { clsNavegacion.abrirDestinoMedico(this, MisPacientesActivity::class.java) }
    }

    fun mostrarMes(titulo: String, puedeRetroceder: Boolean) {
        txtMes.text = titulo
        btnMesAnterior.isEnabled = puedeRetroceder
        val color = if (puedeRetroceder) R.color.negro else R.color.gris_inactivo
        ImageViewCompat.setImageTintList(btnMesAnterior, ColorStateList.valueOf(getColor(color)))
    }

    fun mostrarCalendario(dias: List<dataDiaCalendario>) {
        adaptador.actualizar(dias)
    }

    fun mostrarCargandoCitas(cargando: Boolean) {
        prgCitas.visibility = if (cargando) View.VISIBLE else View.GONE
        if (cargando) {
            lytCitasDia.visibility = View.GONE
            txtSinCitasDia.visibility = View.GONE
        }
    }

    fun mostrarCitasDia(fechaLarga: String, citas: List<dataCita>) {
        txtTituloCitas.text = getString(R.string.agenda_citas_del, fechaLarga)
        lytCitasDia.removeAllViews()

        for (cita in citas) {
            val fila = layoutInflater.inflate(R.layout.item_agenda, lytCitasDia, false)
            fila.findViewById<TextView>(R.id.txtAgendaHora).text = cita.hora
            fila.findViewById<TextView>(R.id.txtAgendaPaciente).text = cita.paciente
            clsEstilos.pintarEstado(fila.findViewById(R.id.txtAgendaEstado), cita.estado)
            if (lytCitasDia.childCount == 0) {
                fila.findViewById<View>(R.id.vDivisor).visibility = View.GONE
            }
            fila.findViewById<View>(R.id.lytFilaCita).setOnClickListener { abrirDetalle(cita.id) }
            lytCitasDia.addView(fila)
        }

        lytCitasDia.visibility = View.VISIBLE
        txtSinCitasDia.visibility = View.GONE
    }

    fun mostrarSinCitasDia(fechaLarga: String, mensaje: String) {
        txtTituloCitas.text = getString(R.string.agenda_citas_del, fechaLarga)
        lytCitasDia.removeAllViews()
        lytCitasDia.visibility = View.GONE
        txtSinCitasDia.text = mensaje
        txtSinCitasDia.visibility = View.VISIBLE
    }

    private fun abrirDetalle(idCita: Int) {
        val intent = Intent(this, DetalleCitaMedicoActivity::class.java)
        intent.putExtra("ID_CITA", idCita)
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