package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataRestablecerPeticion(
    @SerializedName("correo") val correo: String = "",
    @SerializedName("codigo") val codigo: String = "",
    @SerializedName("contrasena") val contrasena: String = "",
    @SerializedName("confirmacion") val confirmacion: String = ""
)
