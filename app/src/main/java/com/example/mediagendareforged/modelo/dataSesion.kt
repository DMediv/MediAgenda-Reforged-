package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName
data class dataSesion(
    @SerializedName("token") val token: String = "",
    @SerializedName("rol") val rol: String = "",
    @SerializedName("id_usuario") val idUsuario: Int = 0,
    @SerializedName("id_paciente") val idPaciente: Int? = null,
    @SerializedName("id_medico") val idMedico: Int? = null,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("especialidad") val especialidad: String? = null
)
