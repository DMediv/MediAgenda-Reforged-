package com.example.mediagendareforged.modelo

import com.google.gson.annotations.SerializedName

data class dataEspecialidad(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = ""
) {
    override fun toString(): String {
        return nombre
    }
}
