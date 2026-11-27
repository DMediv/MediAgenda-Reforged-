package com.example.mediagendareforged.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.modelo.dataCita
import com.example.mediagendareforged.presentador.clsFechas
import com.example.mediagendareforged.vista.clsEstilos

class clsCitasAdaptador(private val alTocar: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<clsCitasAdaptador.Fila>() {

    private var citas: List<dataCita> = emptyList()

    fun actualizar(lista: List<dataCita>) {
        citas = lista
        notifyDataSetChanged()
    }

    class Fila(vista: View) : RecyclerView.ViewHolder(vista) {
        val txtMes: TextView = vista.findViewById(R.id.txtCitaMes)
        val txtDia: TextView = vista.findViewById(R.id.txtCitaDia)
        val txtHora: TextView = vista.findViewById(R.id.txtCitaHora)
        val txtPersona: TextView = vista.findViewById(R.id.txtCitaPersona)
        val txtDetalle: TextView = vista.findViewById(R.id.txtCitaDetalle)
        val txtEstado: TextView = vista.findViewById(R.id.txtCitaEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fila {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.tarjeta_cita, parent, false)

        val parametros = vista.layoutParams as ViewGroup.MarginLayoutParams
        parametros.bottomMargin = parent.resources.getDimensionPixelSize(R.dimen.separacion_lista)
        return Fila(vista)
    }

    override fun onBindViewHolder(fila: Fila, posicion: Int) {
        val cita = citas[posicion]
        fila.txtMes.text = clsFechas.mesCorto(cita.fecha)
        fila.txtDia.text = clsFechas.dia(cita.fecha)
        fila.txtHora.text = cita.hora
        fila.txtPersona.text = cita.medico
        fila.txtDetalle.text = "${cita.especialidad} · ${cita.consultorio}"
        clsEstilos.pintarEstado(fila.txtEstado, cita.estado)

        val accion = alTocar
        if (accion != null) {
            fila.itemView.isClickable = true
            fila.itemView.setOnClickListener { accion(cita.id) }
        } else {
            fila.itemView.setOnClickListener(null)
            fila.itemView.isClickable = false
        }
    }

    override fun getItemCount(): Int = citas.size
}