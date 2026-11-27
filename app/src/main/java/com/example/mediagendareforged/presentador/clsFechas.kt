package com.example.mediagendareforged.presentador
import java.util.Calendar
import java.util.Locale

object clsFechas {

    private val MESES = arrayOf("ene", "feb", "mar", "abr", "may", "jun",
        "jul", "ago", "sep", "oct", "nov", "dic")

    private val MESES_LARGOS = arrayOf("enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre")
    private val DIAS = arrayOf("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado")

    private val DIAS_CORTOS = arrayOf("dom", "lun", "mar", "mié", "jue", "vie", "sáb")

    fun dia(fecha: String): String {
        if (fecha.length < 10) return ""
        return fecha.substring(8, 10).toInt().toString()
    }

    fun mesCorto(fecha: String): String {
        if (fecha.length < 10) return ""
        return MESES[fecha.substring(5, 7).toInt() - 1]
    }

    fun fechaLarga(fecha: String): String {
        if (fecha.length < 10) return ""
        val anio = fecha.substring(0, 4).toInt()
        val mes = fecha.substring(5, 7).toInt()
        val dia = fecha.substring(8, 10).toInt()

        val calendario = Calendar.getInstance()
        calendario.set(anio, mes - 1, dia)                     // en Calendar, enero = 0
        val diaSemana = DIAS[calendario.get(Calendar.DAY_OF_WEEK) - 1]   // domingo = 1

        return "$diaSemana $dia de ${MESES_LARGOS[mes - 1]} de $anio"
    }

    fun rangoHora(hora: String): String {
        if (hora.length < 5) return hora
        val fin = hora.substring(0, 2).toInt() * 60 + hora.substring(3, 5).toInt() + 30
        return "$hora – " + String.format(Locale.ROOT, "%02d:%02d", fin / 60, fin % 60)
    }

    fun fechaCorta(fecha: String): String {
        if (fecha.length < 10) return ""
        val anio = fecha.substring(0, 4).toInt()
        val mes = fecha.substring(5, 7).toInt()
        val dia = fecha.substring(8, 10).toInt()

        val calendario = Calendar.getInstance()
        calendario.set(anio, mes - 1, dia)
        val diaSemana = DIAS_CORTOS[calendario.get(Calendar.DAY_OF_WEEK) - 1]

        return "$diaSemana $dia ${MESES[mes - 1]}"
    }

    fun fechaCompleta(fecha: String): String {
        if (fecha.length < 10) return ""
        val anio = fecha.substring(0, 4).toInt()
        val mes = fecha.substring(5, 7).toInt()
        val dia = fecha.substring(8, 10).toInt()
        return "$dia de ${MESES_LARGOS[mes - 1]} de $anio"
    }

    fun hoy(): String {
        val c = Calendar.getInstance()
        return String.format(Locale.ROOT, "%04d-%02d-%02d",
            c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
    }

    fun nombreMes(mes: Int, anio: Int): String {
        return "${MESES_LARGOS[mes]} $anio"
    }


}