package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataCodigoPeticion(
    @SerializedName("correo") val correo: String = "",
    @SerializedName("codigo") val codigo: String = ""
)
