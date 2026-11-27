package com.example.mediagendareforged.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediagendareforged.R
import com.example.mediagendareforged.modelo.dataPacienteResumen
import com.example.mediagendareforged.vista.clsEstilos

class clsPacientesAdaptador(private val alTocar: (Int) -> Unit) :
    RecyclerView.Adapter<clsPacientesAdaptador.Fila>() {

    private var pacientes: List<dataPacienteResumen> = emptyList()

    fun actualizar(lista: List<dataPacienteResumen>) {
        pacientes = lista
        notifyDataSetChanged()
    }

    class Fila(vista: View) : RecyclerView.ViewHolder(vista) {
        val txtIniciales: TextView = vista.findViewById(R.id.txtIniciales)
        val txtNombre: TextView = vista.findViewById(R.id.txtNombrePaciente)
        val txtUltimaCita: TextView = vista.findViewById(R.id.txtUltimaCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fila {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paciente, parent, false)
        return Fila(vista)
    }

    override fun onBindViewHolder(fila: Fila, posicion: Int) {
        val paciente = pacientes[posicion]
        fila.txtIniciales.text = clsEstilos.iniciales(paciente.nombre)
        fila.txtNombre.text = paciente.nombre
        fila.txtUltimaCita.text = fila.itemView.context
            .getString(R.string.pacientes_ultima_cita, paciente.ultimaCita)
        fila.itemView.setOnClickListener { alTocar(paciente.id) }
    }

    override fun getItemCount(): Int = pacientes.size

}