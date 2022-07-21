package ru.stroesku.kmm.domain.exception.factory

import ru.stroesku.kmm.domain.model.ErrorModel
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import ru.stroesku.kmm.data.response.ErrorResponse
import ru.stroesku.kmm.domain.exception.ApsException
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.domain.exception.UnauthenticatedException
import timber.log.Timber

object ApsExceptionFactory {
    suspend fun parseKtorClientExceptionToDtkException(clientException: ClientRequestException): ApsException {
        val array: ByteArray = clientException.response.body()
        val string = String(array)
        val statusCode = clientException.response.status.value
        val responseJson = Json.parseToJsonElement(string).jsonObject
        val errorsJson = responseJson["errors"]?.jsonObject
        val message = responseJson["message"].toString().replace("\"", "")
        val errorMap: MutableMap<String, String> = mutableMapOf()
        val errorResponse = ErrorResponse(message, errorMap)
        Timber.e("Error message: $message")
        errorsJson?.forEach {
            val value = (it.value as List<*>).first().toString().replace("\"", "")
            Timber.e("Error json $value")
            errorMap[it.key] = value
        }
        return buildException(statusCode, errorResponse)
    }

    private fun buildException(code: Int, errorResponse: ErrorResponse): ApsException {
        val errorModel = ErrorModel(
            code,
            errorResponse.message,
            errorResponse.errors.values.map { it }
        )
        return when (code) {
            401 -> UnauthenticatedException(errorModel)
            else -> ResponseException(errorModel)
        }
    }
}