package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataCorreoPeticion(
    @SerializedName("correo") val correo: String = ""
)
