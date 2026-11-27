package com.example.mediagendareforged.modelo

data class dataDiaCalendario(

    val fecha: String = "",
    val numero: Int = 0,
    val esPasado: Boolean = false,
    val tieneCitas: Boolean = false,
    val seleccionado: Boolean = false
)
