package com.example.mediagendareforged.presentador

object clsFechas {

    private val MESES = arrayOf("ene", "feb", "mar", "abr", "may", "jun",
        "jul", "ago", "sep", "oct", "nov", "dic")

    fun dia(fecha: String): String {
        if (fecha.length < 10) return ""
        return fecha.substring(8, 10).toInt().toString()
    }

    fun mesCorto(fecha: String): String {
        if (fecha.length < 10) return ""
        return MESES[fecha.substring(5, 7).toInt() - 1]
    }
}