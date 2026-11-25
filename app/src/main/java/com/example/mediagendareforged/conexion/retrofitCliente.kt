package com.example.mediagendareforged.conexion
import com.example.mediagendareforged.modelo.*
import com.example.mediagendareforged.interfaceApi.apiService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.mediagendareforged.BuildConfig
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

object retrofitCliente {

    const val BASE_URL = "https://mediagenda.4buthh.com/api/"

    const val EXITO = "success"
    const val ERROR = "error"
    const val SIN_CONEXION = "sin_conexion"
    const val SESION_EXPIRADA = "sesion_expirada"

    var token: String = ""

    private val clienteHttp: OkHttpClient by lazy {
        val registro = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
            redactHeader("Authorization")
        }
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor { cadena ->
                val peticion = cadena.request()
                val peticionConToken = if (token.isNotEmpty()) {
                    peticion.newBuilder().header("Authorization", "Bearer $token").build()
                } else {
                    peticion
                }
                cadena.proceed(peticionConToken)
            }
            .addInterceptor(registro)
            .build()
    }

    val api: apiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
    }

    private fun <T> leerRespuesta(respuesta: Response<dataRespuesta<T>>): dataRespuesta<T> {
        val cuerpo = respuesta.body()
        if (respuesta.isSuccessful && cuerpo != null) {
            return cuerpo
        }
        return try {
            val error = Gson().fromJson(respuesta.errorBody()?.string(), dataRespuesta::class.java)
            dataRespuesta(status = ERROR, message = error.message, data = null)
        } catch (e: Exception) {
            dataRespuesta(status = ERROR, message = "Ocurrió un error (${respuesta.code()}). Intenta más tarde.", data = null)
        }
    }

    suspend fun <T> llamar(peticion: suspend () -> Response<dataRespuesta<T>>): dataRespuesta<T> {
        return try {
            val respuesta = peticion()

            if (respuesta.code() == 401 && token.isNotEmpty()) {
                token = ""
                return dataRespuesta(status = SESION_EXPIRADA, message = "Tu sesión expiró. Inicia sesión de nuevo.")
            }
            leerRespuesta(respuesta)
        } catch (e: IOException) {
            dataRespuesta(status = SIN_CONEXION, message = "Sin conexión. Revisa tu conexión a internet e intenta de nuevo.")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            dataRespuesta(status = ERROR, message = "Ocurrió un error inesperado. Intenta más tarde.")
        }
    }
}