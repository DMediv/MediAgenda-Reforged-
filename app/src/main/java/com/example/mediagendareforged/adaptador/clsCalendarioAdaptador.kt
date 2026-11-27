package com.example.mediagendareforged.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.modelo.dataDiaCalendario

class clsCalendarioAdaptador(private val alTocar: (String) -> Unit) :
    RecyclerView.Adapter<clsCalendarioAdaptador.Celda>() {

    private var dias: List<dataDiaCalendario> = emptyList()

    fun actualizar(lista: List<dataDiaCalendario>) {
        dias = lista
        notifyDataSetChanged()
    }

    class Celda(vista: View) : RecyclerView.ViewHolder(vista) {
        val txtNumero: TextView = vista.findViewById(R.id.txtNumeroDia)
        val vPunto: View = vista.findViewById(R.id.vPuntoCitas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Celda {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dia, parent, false)
        return Celda(vista)
    }

    override fun onBindViewHolder(celda: Celda, posicion: Int) {
        val dia = dias[posicion]
        val contexto = celda.itemView.context

        if (dia.numero == 0) {
            celda.txtNumero.text = ""
            celda.txtNumero.background = null
            celda.vPunto.visibility = View.INVISIBLE
            celda.itemView.setOnClickListener(null)
            celda.itemView.isClickable = false
            return
        }

        celda.txtNumero.text = dia.numero.toString()
        when {
            dia.seleccionado -> {
                celda.txtNumero.setBackgroundResource(R.drawable.circulo_dia_seleccionado)
                celda.txtNumero.setTextColor(contexto.getColor(R.color.blanco))
            }
            dia.esPasado -> {
                celda.txtNumero.background = null
                celda.txtNumero.setTextColor(contexto.getColor(R.color.gris_inactivo))
            }
            else -> {
                celda.txtNumero.background = null
                celda.txtNumero.setTextColor(contexto.getColor(R.color.negro))
            }
        }

        celda.vPunto.visibility =
            if (dia.tieneCitas && !dia.esPasado) View.VISIBLE else View.INVISIBLE

        if (dia.esPasado) {
            celda.itemView.setOnClickListener(null)
            celda.itemView.isClickable = false
        } else {
            celda.itemView.setOnClickListener { alTocar(dia.fecha) }
        }
    }

    override fun getItemCount(): Int = dias.size
}