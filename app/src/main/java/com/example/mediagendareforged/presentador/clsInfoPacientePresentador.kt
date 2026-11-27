package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsMedicoModelo
import com.example.mediagendareforged.vista.InfoPacienteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class clsInfoPacientePresentador(private val vista: InfoPacienteActivity) {

    private val modelo = clsMedicoModelo()

    fun cargarInfo(idPaciente: Int) {
        if (idPaciente == 0) {
            vista.mostrarErrorYCerrar("El paciente no existe.")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.obtenerInfoPaciente(idPaciente)

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                val info = resultado.data

                when {
                    resultado.status == retrofitCliente.EXITO && info != null -> {
                        val p = info.paciente
                        vista.mostrarPaciente(
                            "${p.nombres} ${p.apellidos}",
                            "${clsFechas.fechaCompleta(p.fechaNacimiento)} (${p.edad} años)",
                            p.genero,
                            p.telefono,
                            "${p.direccion}, ${p.ciudad}, ${p.estado}, C.P. ${p.codigoPostal}",
                            p.correo
                        )

                        val h = info.historialMedico
                        if (h == null) {
                            vista.ocultarHistorial()          // sin citas no canceladas (P-19)
                        } else {
                            vista.mostrarHistorial(
                                h.tipoSangre,
                                String.format(Locale.ROOT, "%.1f kg", h.peso),
                                String.format(Locale.ROOT, "%.2f m", h.estatura),
                                h.alergias,
                                h.enfermedadesCronicas,
                                h.medicamentosActuales,
                                h.cirugiasPrevias,
                                h.antecedentesFamiliares,
                                h.tabaquismo,
                                h.consumoAlcohol
                            )
                        }
                    }
                    resultado.status == retrofitCliente.SESION_EXPIRADA -> vista.abrirSesionExpirada()
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarErrorYCerrar(resultado.message)   // p. ej. 403
                }
            }
        }
    }
}