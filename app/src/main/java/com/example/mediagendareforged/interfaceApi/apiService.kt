package com.example.mediagendareforged.interfaceApi

import com.example.mediagendareforged.modelo.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface apiService {
    @POST("iniciar_sesion.php")
    suspend fun iniciarSesion(@Body datos: dataLoginPeticion): Response<dataRespuesta<dataSesion>>

    @GET("verificar_disponibilidad.php")
    suspend fun verificarDisponibilidad(
        @Query("correo") correo: String? = null,
        @Query("usuario") usuario: String? = null
    ): Response<dataRespuesta<dataDisponibilidad>>

    @POST("registrar_paciente.php")
    suspend fun registrarPaciente(@Body datos: dataRegistroPeticion): Response<dataRespuesta<dataRegistro>>

    @POST("cerrar_sesion.php")
    suspend fun cerrarSesion(): Response<dataRespuesta<Any>>

    @POST("solicitar_codigo.php")
    suspend fun solicitarCodigo(@Body datos: dataCorreoPeticion): Response<dataRespuesta<Any>>

    @POST("verificar_codigo.php")
    suspend fun verificarCodigo(@Body datos: dataCodigoPeticion): Response<dataRespuesta<Any>>

    @POST("restablecer_contrasena.php")
    suspend fun restablecerContrasena(@Body datos: dataRestablecerPeticion): Response<dataRespuesta<Any>>

    //Catalogos de las vistas

    @GET("obtener_especialidades.php")
    suspend fun obtenerEspecialidades(): Response<dataRespuesta<List<dataEspecialidad>>>

    @GET("obtener_medicos.php")
    suspend fun obtenerMedicos(
        @Query("id_especialidad") idEspecialidad: Int? = null,
        @Query("busqueda") busqueda: String? = null
    ): Response<dataRespuesta<List<dataMedico>>>

    @GET("obtener_dias_disponibles.php")
    suspend fun obtenerDiasDisponibles(
        @Query("id_medico") idMedico: Int,
        @Query("mes") mes: String
    ): Response<dataRespuesta<dataDiasMes>>

    @GET("obtener_horarios_disponibles.php")
    suspend fun obtenerHorariosDisponibles(
        @Query("id_medico") idMedico: Int,
        @Query("fecha") fecha: String
    ): Response<dataRespuesta<dataHorariosDia>>

    //Panel del Usuario

    @GET("obtener_perfil.php")
    suspend fun obtenerPerfil(): Response<dataRespuesta<dataPerfil>>

    @POST("actualizar_perfil.php")
    suspend fun actualizarPerfil(@Body datos: dataPerfilPeticion): Response<dataRespuesta<Any>>

    @POST("agendar_cita.php")
    suspend fun agendarCita(@Body datos: dataAgendarPeticion): Response<dataRespuesta<dataCita>>

    @GET("obtener_mis_citas.php")
    suspend fun obtenerMisCitas(
        @Query("tipo") tipo: String,
        @Query("estado") estado: String? = null
    ): Response<dataRespuesta<List<dataCita>>>

    @POST("cancelar_cita.php")
    suspend fun cancelarCita(@Body datos: dataCancelarPeticion): Response<dataRespuesta<Any>>

    @POST("reprogramar_cita.php")
    suspend fun reprogramarCita(@Body datos: dataReprogramarPeticion): Response<dataRespuesta<dataCita>>

    @GET("obtener_detalle_cita.php")
    suspend fun obtenerDetalleCita(@Query("id_cita") idCita: Int): Response<dataRespuesta<dataCita>>

    //Panel del Medico

    @GET("obtener_panel_medico.php")
    suspend fun obtenerPanelMedico(): Response<dataRespuesta<dataPanelMedico>>

    @GET("obtener_dias_con_citas.php")
    suspend fun obtenerDiasConCitas(@Query("mes") mes: String): Response<dataRespuesta<dataDiasMes>>

    @GET("obtener_agenda.php")
    suspend fun obtenerAgenda(@Query("fecha") fecha: String): Response<dataRespuesta<List<dataCita>>>


    @GET("obtener_mis_pacientes.php")
    suspend fun obtenerMisPacientes(
        @Query("busqueda") busqueda: String? = null
    ): Response<dataRespuesta<List<dataPacienteResumen>>>


    @GET("obtener_info_paciente.php")
    suspend fun obtenerInfoPaciente(@Query("id_paciente") idPaciente: Int): Response<dataRespuesta<dataInfoPaciente>>


    @GET("obtener_historial_citas_paciente.php")
    suspend fun obtenerHistorialCitasPaciente(
        @Query("id_paciente") idPaciente: Int,
        @Query("ambito") ambito: String
    ): Response<dataRespuesta<List<dataCita>>>
}