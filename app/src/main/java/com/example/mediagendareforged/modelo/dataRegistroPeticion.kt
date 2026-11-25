package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataRegistroPeticion(
    @SerializedName("nombres") val nombres: String = "",
    @SerializedName("apellidos") val apellidos: String = "",
    @SerializedName("fecha_nacimiento") val fechaNacimiento: String = "",
    @SerializedName("genero") val genero: String = "",
    @SerializedName("telefono") val telefono: String = "",
    @SerializedName("codigo_postal") val codigoPostal: String = "",
    @SerializedName("ciudad") val ciudad: String = "",
    @SerializedName("estado") val estado: String = "",
    @SerializedName("direccion") val direccion: String = "",
    @SerializedName("correo") val correo: String = "",
    @SerializedName("tipo_sangre") val tipoSangre: String = "",
    @SerializedName("peso") val peso: Double = 0.0,
    @SerializedName("estatura") val estatura: Double = 0.0,
    @SerializedName("alergias") val alergias: String = "",
    @SerializedName("enfermedades_cronicas") val enfermedadesCronicas: String = "",
    @SerializedName("medicamentos_actuales") val medicamentosActuales: String = "",
    @SerializedName("cirugias_previas") val cirugiasPrevias: String = "",
    @SerializedName("antecedentes_familiares") val antecedentesFamiliares: String = "",
    @SerializedName("tabaquismo") val tabaquismo: String = "",
    @SerializedName("consumo_alcohol") val consumoAlcohol: String = "",
    @SerializedName("usuario") val usuario: String = "",
    @SerializedName("contrasena") val contrasena: String = "",
    @SerializedName("confirmacion") val confirmacion: String = ""
)
