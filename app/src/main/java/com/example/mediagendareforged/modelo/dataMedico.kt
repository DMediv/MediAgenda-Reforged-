package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataMedico(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("id_especialidad") val idEspecialidad: Int = 0,
    @SerializedName("especialidad") val especialidad: String = "",
    @SerializedName("id_consultorio") val idConsultorio: Int = 0,
    @SerializedName("consultorio") val consultorio: String = "",
    @SerializedName("ubicacion") val ubicacion: String = ""
) {
    override fun toString(): String {
        return nombre
    }
}
