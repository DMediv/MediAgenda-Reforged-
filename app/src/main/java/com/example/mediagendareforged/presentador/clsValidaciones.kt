package com.example.mediagendareforged.presentador

object clsValidaciones {

    const val MENSAJE_OBLIGATORIO = "Este campo es obligatorio."

    fun validarObligatorio(valor: String): String? {
        return if (valor.isBlank()) MENSAJE_OBLIGATORIO else null
    }
}