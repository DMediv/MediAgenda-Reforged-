package com.example.mediagenda.modelo

import com.google.gson.annotations.SerializedName

data class dataRespuesta<T>(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: T? = null
)
