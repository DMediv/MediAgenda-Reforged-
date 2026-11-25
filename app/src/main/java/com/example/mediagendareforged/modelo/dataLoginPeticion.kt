package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName
data class dataLoginPeticion(
    @SerializedName("usuario") val usuario: String = "",
    @SerializedName("contrasena") val contrasena: String = ""
)
