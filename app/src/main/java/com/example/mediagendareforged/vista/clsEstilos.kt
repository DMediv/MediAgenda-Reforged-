package com.example.mediagendareforged.vista

import android.content.res.ColorStateList
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import com.example.mediagendareforged.R

object clsEstilos {

    fun activarDestino(destino: LinearLayout) {
        val contexto = destino.context
        val indicador = destino.getChildAt(0) as FrameLayout
        val icono = indicador.getChildAt(0) as ImageView
        val texto = destino.getChildAt(1) as TextView
        val dosDp = (2 * contexto.resources.displayMetrics.density).toInt()

        indicador.setBackgroundResource(R.drawable.indicador_navegacion)
        icono.setPadding(dosDp, dosDp, dosDp, dosDp)
        ImageViewCompat.setImageTintList(icono,
            ColorStateList.valueOf(contexto.getColor(R.color.azul_navegacion)))
        texto.setTextColor(contexto.getColor(R.color.azul_primario))
        texto.typeface = ResourcesCompat.getFont(contexto, R.font.inter_semibold)
        destino.isClickable = false
    }

    fun pintarEstado(etiqueta: TextView, estado: String) {
        val color = when (estado.lowercase()) {
            "programada" -> R.color.estado_programada
            "reprogramada" -> R.color.estado_reprogramada
            "atendida" -> R.color.estado_atendida
            "cancelada" -> R.color.estado_cancelada
            else -> R.color.fondo
        }
        etiqueta.text = estado
        etiqueta.backgroundTintList = ColorStateList.valueOf(etiqueta.context.getColor(color))
    }

    fun iniciales(nombre: String): String {
        return nombre.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
    }
}