package com.example.mediagendareforged.presentador

import com.example.mediagendareforged.conexion.retrofitCliente
import com.example.mediagendareforged.modelo.clsAutentificacionModelo
import com.example.mediagendareforged.modelo.dataRegistroPeticion
import com.example.mediagendareforged.vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsRegistroPresentador(private val vista: RegistroActivity) {

    private val modelo = clsAutentificacionModelo()

    private var datos = dataRegistroPeticion()

    fun validarPaso1(nombres: String, apellidos: String, fechaNacimiento: String, genero: String) {
        val errorNombres = clsValidaciones.validarTexto(nombres, 60)
        val errorApellidos = clsValidaciones.validarTexto(apellidos, 80)
        val errorFecha = clsValidaciones.validarFechaNacimiento(fechaNacimiento)
        val errorGenero = clsValidaciones.validarObligatorio(genero)

        vista.mostrarErroresPaso1(errorNombres, errorApellidos, errorFecha, errorGenero)
        if (hayErrores(errorNombres, errorApellidos, errorFecha, errorGenero)) return

        datos = datos.copy(
            nombres = nombres.trim(),
            apellidos = apellidos.trim(),
            fechaNacimiento = fechaNacimiento,
            genero = genero
        )
        vista.mostrarPaso(1)
    }

    fun validarPaso2(
        telefono: String, codigoPostal: String, ciudad: String,
        estado: String, direccion: String, correo: String
    ) {
        val correoLimpio = correo.trim().lowercase()

        val errorTelefono = clsValidaciones.validarTelefono(telefono)
        val errorCodigoPostal = clsValidaciones.validarCodigoPostal(codigoPostal)
        val errorCiudad = clsValidaciones.validarTexto(ciudad, 60)
        val errorEstado = clsValidaciones.validarTexto(estado, 60)
        val errorDireccion = clsValidaciones.validarTexto(direccion, 150)
        val errorCorreo = clsValidaciones.validarCorreo(correoLimpio)

        vista.mostrarErroresPaso2(errorTelefono, errorCodigoPostal, errorCiudad, errorEstado, errorDireccion, errorCorreo)
        if (hayErrores(errorTelefono, errorCodigoPostal, errorCiudad, errorEstado, errorDireccion, errorCorreo)) return

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {
            val resultado = modelo.verificarCorreo(correoLimpio)

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                when {
                    resultado.status == retrofitCliente.EXITO && resultado.data?.disponible == true -> {
                        datos = datos.copy(
                            telefono = telefono.trim(),
                            codigoPostal = codigoPostal.trim(),
                            ciudad = ciudad.trim(),
                            estado = estado.trim(),
                            direccion = direccion.trim(),
                            correo = correoLimpio
                        )
                        vista.mostrarPaso(2)
                    }
                    resultado.status == retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    // «Este correo ya está registrado.» u otro error del campo, en línea (A4)
                    else -> vista.mostrarErrorCorreo(resultado.message)
                }
            }
        }
    }

    fun validarPaso3(
        tipoSangre: String, peso: String, estatura: String,
        alergias: String, enfermedades: String, medicamentos: String,
        cirugias: String, antecedentes: String, tabaquismo: String, alcohol: String
    ) {
        val errorTipoSangre = clsValidaciones.validarObligatorio(tipoSangre)
        val errorPeso = clsValidaciones.validarPeso(peso)
        val errorEstatura = clsValidaciones.validarEstatura(estatura)
        val errorAlergias = clsValidaciones.validarTexto(alergias, 255)
        val errorEnfermedades = clsValidaciones.validarTexto(enfermedades, 255)
        val errorMedicamentos = clsValidaciones.validarTexto(medicamentos, 255)
        val errorCirugias = clsValidaciones.validarTexto(cirugias, 255)
        val errorAntecedentes = clsValidaciones.validarTexto(antecedentes, 255)
        val errorTabaquismo = clsValidaciones.validarObligatorio(tabaquismo)
        val errorAlcohol = clsValidaciones.validarObligatorio(alcohol)

        vista.mostrarErroresPaso3(
            tipoSangre = errorTipoSangre, peso = errorPeso, estatura = errorEstatura,
            alergias = errorAlergias, enfermedades = errorEnfermedades, medicamentos = errorMedicamentos,
            cirugias = errorCirugias, antecedentes = errorAntecedentes,
            tabaquismo = errorTabaquismo, alcohol = errorAlcohol
        )
        if (hayErrores(
                errorTipoSangre, errorPeso, errorEstatura, errorAlergias, errorEnfermedades,
                errorMedicamentos, errorCirugias, errorAntecedentes, errorTabaquismo, errorAlcohol
            )
        ) return

        datos = datos.copy(
            tipoSangre = tipoSangre,
            peso = peso.trim().replace(',', '.').toDouble(),
            estatura = estatura.trim().replace(',', '.').toDouble(),
            alergias = alergias.trim(),
            enfermedadesCronicas = enfermedades.trim(),
            medicamentosActuales = medicamentos.trim(),
            cirugiasPrevias = cirugias.trim(),
            antecedentesFamiliares = antecedentes.trim(),
            tabaquismo = tabaquismo,
            consumoAlcohol = alcohol
        )
        vista.mostrarPaso(3)
    }

    fun registrar(usuario: String, contrasena: String, confirmacion: String) {
        val usuarioLimpio = usuario.trim()

        val errorUsuario = clsValidaciones.validarUsuario(usuarioLimpio)
        val errorContrasena = clsValidaciones.validarContrasena(contrasena)
        val errorConfirmacion = clsValidaciones.validarConfirmacion(contrasena, confirmacion)

        vista.mostrarErroresPaso4(errorUsuario, errorContrasena, errorConfirmacion)
        if (hayErrores(errorUsuario, errorContrasena, errorConfirmacion)) return

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.IO).launch {

            // 1. ¿El usuario está libre?
            val verificacion = modelo.verificarUsuario(usuarioLimpio)
            if (verificacion.status != retrofitCliente.EXITO || verificacion.data?.disponible != true) {
                withContext(Dispatchers.Main) {
                    vista.mostrarCargando(false)
                    if (verificacion.status == retrofitCliente.SIN_CONEXION) vista.abrirSinConexion()
                    else vista.mostrarErrorUsuario(verificacion.message)   // «Este usuario ya está en uso.»
                }
                return@launch
            }

            val peticion = datos.copy(usuario = usuarioLimpio, contrasena = contrasena, confirmacion = confirmacion)
            val resultado = modelo.registrarPaciente(peticion)

            withContext(Dispatchers.Main) {
                vista.mostrarCargando(false)
                when (resultado.status) {
                    retrofitCliente.EXITO -> vista.registroExitoso(resultado.message)
                    retrofitCliente.SIN_CONEXION -> vista.abrirSinConexion()
                    else -> vista.mostrarAviso(resultado.message)   // p. ej. 409 si alguien ocupó el correo justo antes
                }
            }
        }
    }

    fun anterior(pasoActual: Int) {
        if (pasoActual > 0) vista.mostrarPaso(pasoActual - 1) else vista.cerrar()
    }

    private fun hayErrores(vararg errores: String?): Boolean = errores.any { it != null }
}