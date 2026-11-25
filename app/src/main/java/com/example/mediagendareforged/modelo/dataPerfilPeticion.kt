package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataPerfilPeticion(
    @SerializedName("nombres") val nombres: String = "",
    @SerializedName("apellidos") val apellidos: String = "",
    @SerializedName("fecha_nacimiento") val fechaNacimiento: String = "",
    @SerializedName("genero") val genero: String = "",
    @SerializedName("telefono") val telefono: String = "",
    @SerializedName("codigo_postal") val codigoPostal: String = "",
    @SerializedName("ciudad") val ciudad: String = "",
    @SerializedName("estado") val estado: String = "",
    @SerializedName("direccion") val direccion: String = "",
    @SerializedName("correo") val correo: String = ""
)
