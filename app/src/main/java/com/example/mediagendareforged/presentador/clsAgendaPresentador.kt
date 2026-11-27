package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.modelo.dataDiaCalendario
import com.example.mediagendareforged.vista.AgendaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale

class clsAgendaPresentador(private val vista: AgendaActivity) {

    private val modelo = clsMedicoModelo()

    private val hoy = clsFechas.hoy()
    private val anioActual = hoy.substring(0, 4).toInt()
    private val mesActual = hoy.substring(5, 7).toInt() - 1

    private var anio = anioActual
    private var mes = mesActual
    private var seleccionada = hoy
    private var diasConCitas: Set<String> = emptySet()

    private var mesPedido = ""
    private var fechaPedida = ""

    fun recargar() {
        cargarMes()
        cargarAgenda(seleccionada)
    }

    fun mesAnterior() {
        if (esMesActual()) return                           // no se consultan meses pasados
        if (mes == 0) { mes = 11; anio-- } else mes--
        cargarMes()
    }

    fun mesSiguiente() {
        if (mes == 11) { mes = 0; anio++ } else mes++
        cargarMes()
    }

    fun seleccionarDia(fecha: String) {
        if (fecha < hoy || fecha == seleccionada) return
        seleccionada = fecha
        vista.mostrarCalendario(construirDias())
        cargarAgenda(fecha)
    }

    private fun esMesActual() = anio == anioActual && mes == mesActual

    private fun cargarMes() {
        val textoMes = String.format(Locale.ROOT, "%04d-%02d", anio, mes + 1)   // "2026-11"
        mesPedido = textoMes
        diasConCitas = emptySet()
        vista.mostrarMes(clsFechas.nombreMes(mes, anio), !esMesActual())
        vista.mostrarCalendario(construirDias())

        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerDiasConCitas(textoMes)

            withContext(Dispatchers.Main) {
                if (textoMes != mesPedido) return@withContext   // ya cambió de mes
                val datos = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && datos != null -> {
                        diasConCitas = datos.dias.toSet()
                        vista.mostrarCalendario(construirDias())
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                }
            }
        }
    }

    private fun cargarAgenda(fecha: String) {
        fechaPedida = fecha
        val titulo = clsFechas.fechaLarga(fecha)
        vista.mostrarCargandoCitas(true)

        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerAgenda(fecha)

            withContext(Dispatchers.Main) {
                if (fecha != fechaPedida) return@withContext
                vista.mostrarCargandoCitas(false)
                val citas = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && citas != null -> {
                        if (citas.isEmpty()) vista.mostrarSinCitasDia(titulo, resultado.message)
                        else vista.mostrarCitasDia(titulo, citas)
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarAviso(resultado.message)
                }
            }
        }
    }

    private fun construirDias(): List<dataDiaCalendario> {
        val calendario = Calendar.getInstance()
        calendario.clear()
        calendario.set(anio, mes, 1)

        val diasDelMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH)
        val huecos = (calendario.get(Calendar.DAY_OF_WEEK) + 5) % 7

        val lista = mutableListOf<dataDiaCalendario>()
        repeat(huecos) { lista.add(dataDiaCalendario()) }

        for (dia in 1..diasDelMes) {
            val fecha = String.format(Locale.ROOT, "%04d-%02d-%02d", anio, mes + 1, dia)
            lista.add(
                dataDiaCalendario(
                    fecha = fecha,
                    numero = dia,
                    esPasado = fecha < hoy,
                    tieneCitas = fecha in diasConCitas,
                    seleccionado = fecha == seleccionada
                )
            )
        }
        return lista
    }
}