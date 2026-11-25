package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataRegistro(
    @SerializedName("id_usuario") val idUsuario: Int = 0
)
