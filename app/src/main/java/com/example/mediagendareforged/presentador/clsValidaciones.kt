package com.example.mediagendareforged.presentador

import java.time.LocalDate
import java.time.format.DateTimeParseException

object clsValidaciones {

    const val MENSAJE_OBLIGATORIO = "Este campo es obligatorio."

    private val REGEX_CORREO = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val REGEX_USUARIO = Regex("^[A-Za-z0-9._-]{4,50}$")
    private val REGEX_TELEFONO = Regex("^[0-9]{10}$")
    private val REGEX_CODIGO_POSTAL = Regex("^[0-9]{5}$")

    fun validarObligatorio(valor: String): String? {
        return if (valor.isBlank()) MENSAJE_OBLIGATORIO else null
    }

    fun validarTexto(valor: String, maximo: Int): String? {
        validarObligatorio(valor)?.let { return it }
        return if (valor.trim().length > maximo) "Admite como máximo $maximo caracteres." else null
    }

    fun validarTelefono(telefono: String): String? {
        validarObligatorio(telefono)?.let { return it }
        return if (!REGEX_TELEFONO.matches(telefono.trim())) "El teléfono debe tener 10 dígitos, solo números." else null
    }

    fun validarCodigoPostal(codigo: String): String? {
        validarObligatorio(codigo)?.let { return it }
        return if (!REGEX_CODIGO_POSTAL.matches(codigo.trim())) "El código postal debe tener 5 dígitos, solo números." else null
    }

    fun validarCorreo(correo: String): String? {
        validarTexto(correo, 100)?.let { return it }
        return if (!REGEX_CORREO.matches(correo.trim())) "El correo electrónico no tiene un formato válido." else null
    }

    // fecha en formato AAAA-MM-DD. "hoy" se puede cambiar en las pruebas.
    fun validarFechaNacimiento(fecha: String, hoy: LocalDate = LocalDate.now()): String? {
        validarObligatorio(fecha)?.let { return it }
        val fechaNacimiento = try {
            LocalDate.parse(fecha)
        } catch (e: DateTimeParseException) {
            return "La fecha de nacimiento no es válida."
        }
        return when {
            !fechaNacimiento.isBefore(hoy) -> "La fecha de nacimiento debe ser anterior a hoy."
            fechaNacimiento.isBefore(LocalDate.of(1900, 1, 1)) -> "La fecha de nacimiento no es válida."
            else -> null
        }
    }

    // Rangos provisionales de P-10 / D-10a
    fun validarPeso(peso: String): String? =
        validarDecimal(peso, 1.0, 500.0, "El peso debe estar entre 1 y 500 kg.")

    fun validarEstatura(estatura: String): String? =
        validarDecimal(estatura, 0.30, 2.50, "La estatura debe estar entre 0.30 y 2.50 m.")

    fun validarUsuario(usuario: String): String? {
        validarObligatorio(usuario)?.let { return it }
        return if (!REGEX_USUARIO.matches(usuario.trim()))
            "El usuario debe tener de 4 a 50 caracteres: letras sin acento, números, punto, guion o guion bajo."
        else null
    }

    fun validarContrasena(contrasena: String): String? {
        return when {
            contrasena.isEmpty() -> MENSAJE_OBLIGATORIO
            contrasena.length < 8 -> "La contraseña debe tener al menos 8 caracteres."
            contrasena.length > 72 -> "La contraseña admite como máximo 72 caracteres."
            else -> null
        }
    }

    fun validarConfirmacion(contrasena: String, confirmacion: String): String? {
        return when {
            confirmacion.isEmpty() -> MENSAJE_OBLIGATORIO
            contrasena != confirmacion -> "La contraseña y su confirmación no coinciden."
            else -> null
        }
    }

    private fun validarDecimal(valor: String, minimo: Double, maximo: Double, mensajeRango: String): String? {
        validarObligatorio(valor)?.let { return it }
        val numero = valor.trim().replace(',', '.').toDoubleOrNull()
            ?: return "Escribe solo números."
        return if (numero < minimo || numero > maximo) mensajeRango else null
    }
}