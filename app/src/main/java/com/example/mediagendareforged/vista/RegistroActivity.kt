package com.example.mediagendareforged.vista

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediagendareforged.R
import com.example.mediagendareforged.presentador.clsRegistroPresentador
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {

    private lateinit var presentador: clsRegistroPresentador

    private lateinit var vfPasos: ViewFlipper
    private lateinit var scrPasos: ScrollView
    private lateinit var txtPasoActual: TextView
    private lateinit var circulosPaso: List<TextView>
    private lateinit var btnAnterior: AppCompatButton
    private lateinit var btnSiguiente: AppCompatButton
    private lateinit var prgCargando: ProgressBar
    private lateinit var tilNombres: TextInputLayout
    private lateinit var tilApellidos: TextInputLayout
    private lateinit var tilFechaNacimiento: TextInputLayout
    private lateinit var tilGenero: TextInputLayout
    private var fechaNacimiento = ""
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilCodigoPostal: TextInputLayout
    private lateinit var tilCiudad: TextInputLayout
    private lateinit var tilEstado: TextInputLayout
    private lateinit var tilDireccion: TextInputLayout
    private lateinit var tilCorreo: TextInputLayout
    private lateinit var tilTipoSangre: TextInputLayout
    private lateinit var tilPeso: TextInputLayout
    private lateinit var tilEstatura: TextInputLayout
    private lateinit var tilAlergias: TextInputLayout
    private lateinit var tilEnfermedades: TextInputLayout
    private lateinit var tilMedicamentos: TextInputLayout
    private lateinit var tilCirugias: TextInputLayout
    private lateinit var tilAntecedentes: TextInputLayout
    private lateinit var tilTabaquismo: TextInputLayout
    private lateinit var tilAlcohol: TextInputLayout
    private lateinit var tilUsuario: TextInputLayout
    private lateinit var tilContrasena: TextInputLayout
    private lateinit var tilConfirmacion: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        presentador = clsRegistroPresentador(this)
        configurarListeners()
        mostrarPaso(0)
    }

    private fun enlazarVistas() {
        vfPasos = findViewById(R.id.vfPasos)
        scrPasos = findViewById(R.id.scrPasos)
        txtPasoActual = findViewById(R.id.txtPasoActual)
        circulosPaso = listOf(
            findViewById(R.id.txtPaso1), findViewById(R.id.txtPaso2),
            findViewById(R.id.txtPaso3), findViewById(R.id.txtPaso4)
        )
        btnAnterior = findViewById(R.id.btnAnterior)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        prgCargando = findViewById(R.id.prgCargando)

        tilNombres = findViewById(R.id.tilNombres)
        tilApellidos = findViewById(R.id.tilApellidos)
        tilFechaNacimiento = findViewById(R.id.tilFechaNacimiento)
        tilGenero = findViewById(R.id.tilGenero)

        tilTelefono = findViewById(R.id.tilTelefono)
        tilCodigoPostal = findViewById(R.id.tilCodigoPostal)
        tilCiudad = findViewById(R.id.tilCiudad)
        tilEstado = findViewById(R.id.tilEstado)
        tilDireccion = findViewById(R.id.tilDireccion)
        tilCorreo = findViewById(R.id.tilCorreo)

        tilTipoSangre = findViewById(R.id.tilTipoSangre)
        tilPeso = findViewById(R.id.tilPeso)
        tilEstatura = findViewById(R.id.tilEstatura)
        tilAlergias = findViewById(R.id.tilAlergias)
        tilEnfermedades = findViewById(R.id.tilEnfermedades)
        tilMedicamentos = findViewById(R.id.tilMedicamentos)
        tilCirugias = findViewById(R.id.tilCirugias)
        tilAntecedentes = findViewById(R.id.tilAntecedentes)
        tilTabaquismo = findViewById(R.id.tilTabaquismo)
        tilAlcohol = findViewById(R.id.tilAlcohol)

        tilUsuario = findViewById(R.id.tilUsuario)
        tilContrasena = findViewById(R.id.tilContrasena)
        tilConfirmacion = findViewById(R.id.tilConfirmacion)
    }

    private fun configurarListeners() {
        btnSiguiente.setOnClickListener { enviarPasoActual() }
        btnAnterior.setOnClickListener { presentador.anterior(vfPasos.displayedChild) }
        findViewById<ImageButton>(R.id.btnRegresar).setOnClickListener {
            presentador.anterior(vfPasos.displayedChild)
        }
        // El botón Atrás del teléfono también regresa un paso
        onBackPressedDispatcher.addCallback(this) { presentador.anterior(vfPasos.displayedChild) }

        tilFechaNacimiento.editText?.setOnClickListener { abrirCalendario() }
    }

    private fun enviarPasoActual() {
        when (vfPasos.displayedChild) {
            0 -> presentador.validarPaso1(
                tilNombres.texto(), tilApellidos.texto(), fechaNacimiento, tilGenero.texto()
            )
            1 -> presentador.validarPaso2(
                telefono = tilTelefono.texto(),
                codigoPostal = tilCodigoPostal.texto(),
                ciudad = tilCiudad.texto(),
                estado = tilEstado.texto(),
                direccion = tilDireccion.texto(),
                correo = tilCorreo.texto()
            )
            2 -> presentador.validarPaso3(
                tipoSangre = tilTipoSangre.texto(),
                peso = tilPeso.texto(),
                estatura = tilEstatura.texto(),
                alergias = tilAlergias.texto(),
                enfermedades = tilEnfermedades.texto(),
                medicamentos = tilMedicamentos.texto(),
                cirugias = tilCirugias.texto(),
                antecedentes = tilAntecedentes.texto(),
                tabaquismo = tilTabaquismo.texto(),
                alcohol = tilAlcohol.texto()
            )
            3 -> presentador.registrar(
                tilUsuario.texto(), tilContrasena.texto(), tilConfirmacion.texto()
            )
        }
    }

    private fun abrirCalendario() {
        val hoy = Calendar.getInstance()
        val dialogo = DatePickerDialog(
            this,
            { _, anio, mes, dia ->
                // mes empieza en 0 (enero = 0), por eso se suma 1
                fechaNacimiento = String.format(Locale.ROOT, "%04d-%02d-%02d", anio, mes + 1, dia)
                tilFechaNacimiento.editText?.setText(
                    String.format(Locale.ROOT, "%02d/%02d/%04d", dia, mes + 1, anio)
                )
                tilFechaNacimiento.error = null
            },
            hoy.get(Calendar.YEAR) - 18, hoy.get(Calendar.MONTH), hoy.get(Calendar.DAY_OF_MONTH)
        )
        dialogo.datePicker.maxDate = hoy.timeInMillis - 24L * 60 * 60 * 1000
        dialogo.show()
    }

    fun mostrarPaso(paso: Int) {
        vfPasos.displayedChild = paso
        scrPasos.scrollTo(0, 0)
        txtPasoActual.text = getString(R.string.registro_paso_n, paso + 1)
        btnAnterior.visibility = if (paso == 0) View.GONE else View.VISIBLE
        btnSiguiente.text = getString(if (paso == 3) R.string.registro_registrar else R.string.registro_siguiente)
        actualizarIndicador(paso)
    }

    fun mostrarErroresPaso1(nombres: String?, apellidos: String?, fecha: String?, genero: String?) {
        tilNombres.error = nombres
        tilApellidos.error = apellidos
        tilFechaNacimiento.error = fecha
        tilGenero.error = genero
    }

    fun mostrarErroresPaso2(
        telefono: String?, codigoPostal: String?, ciudad: String?,
        estado: String?, direccion: String?, correo: String?
    ) {
        tilTelefono.error = telefono
        tilCodigoPostal.error = codigoPostal
        tilCiudad.error = ciudad
        tilEstado.error = estado
        tilDireccion.error = direccion
        tilCorreo.error = correo
    }

    fun mostrarErroresPaso3(
        tipoSangre: String?, peso: String?, estatura: String?,
        alergias: String?, enfermedades: String?, medicamentos: String?,
        cirugias: String?, antecedentes: String?, tabaquismo: String?, alcohol: String?
    ) {
        tilTipoSangre.error = tipoSangre
        tilPeso.error = peso
        tilEstatura.error = estatura
        tilAlergias.error = alergias
        tilEnfermedades.error = enfermedades
        tilMedicamentos.error = medicamentos
        tilCirugias.error = cirugias
        tilAntecedentes.error = antecedentes
        tilTabaquismo.error = tabaquismo
        tilAlcohol.error = alcohol
    }

    fun mostrarErroresPaso4(usuario: String?, contrasena: String?, confirmacion: String?) {
        tilUsuario.error = usuario
        tilContrasena.error = contrasena
        tilConfirmacion.error = confirmacion
    }

    fun mostrarErrorCorreo(mensaje: String) {
        tilCorreo.error = mensaje
    }

    fun mostrarErrorUsuario(mensaje: String) {
        tilUsuario.error = mensaje
    }

    fun mostrarCargando(cargando: Boolean) {
        prgCargando.visibility = if (cargando) View.VISIBLE else View.GONE
        btnSiguiente.isEnabled = !cargando
        btnAnterior.isEnabled = !cargando
    }

    fun mostrarAviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun registroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()   // regresa al inicio de sesión
    }

    fun abrirSinConexion() {
        clsNavegacion.abrirSinConexion(this)
    }

    fun cerrar() {
        finish()
    }
    private fun actualizarIndicador(pasoActual: Int) {
        circulosPaso.forEachIndexed { indice, circulo ->
            when {
                indice < pasoActual -> {
                    circulo.setBackgroundResource(R.drawable.paso_activo)
                    circulo.text = "✓"
                    circulo.setTextColor(getColor(R.color.blanco))
                }
                indice == pasoActual -> {
                    circulo.setBackgroundResource(R.drawable.paso_activo)
                    circulo.text = (indice + 1).toString()
                    circulo.setTextColor(getColor(R.color.blanco))
                }
                else -> {
                    circulo.setBackgroundResource(R.drawable.paso_pendiente)
                    circulo.text = (indice + 1).toString()
                    circulo.setTextColor(getColor(R.color.negro))
                }
            }
        }
    }

    private fun TextInputLayout.texto(): String = editText?.text?.toString() ?: ""
}