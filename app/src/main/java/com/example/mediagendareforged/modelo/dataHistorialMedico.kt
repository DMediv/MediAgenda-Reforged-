package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataHistorialMedico(
    @SerializedName("tipo_sangre") val tipoSangre: String = "",
    @SerializedName("peso") val peso: Double = 0.0,
    @SerializedName("estatura") val estatura: Double = 0.0,
    @SerializedName("alergias") val alergias: String = "",
    @SerializedName("enfermedades_cronicas") val enfermedadesCronicas: String = "",
    @SerializedName("medicamentos_actuales") val medicamentosActuales: String = "",
    @SerializedName("cirugias_previas") val cirugiasPrevias: String = "",
    @SerializedName("antecedentes_familiares") val antecedentesFamiliares: String = "",
    @SerializedName("tabaquismo") val tabaquismo: String = "",
    @SerializedName("consumo_alcohol") val consumoAlcohol: String = ""
)
