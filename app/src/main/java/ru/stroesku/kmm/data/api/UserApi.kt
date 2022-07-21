package ru.stroesku.kmm.data.api



import ru.stroesku.kmm.data.request.PhoneAndCodeRequest
import ru.stroesku.kmm.data.request.SignUpRequest
import ru.stroesku.kmm.data.request.PhoneRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.stroesku.kmm.data.dto.AuthorizationResponse

class UserApi(private val client: HttpClient) {

    /**
     * Sign in
     */
    suspend fun sendPhoneSignIn(phone: String): Any {
        return client.post("/cleaners/self/auth/sms-send") {
            setBody(PhoneRequest(phone))
        }.body()
    }

    suspend fun sendSmsSignIn(phone: String, code: String): AuthorizationResponse {
        return client.post("/cleaners/self/auth/sms-check") {
            setBody(PhoneAndCodeRequest(phone, code))
        }.body()
    }


    /**
     * Sign up
     */
    suspend fun sendPhoneSignUp(phone: String): Any {
        return client.post("/cleaners/self/reg/sms-send") {
            setBody(PhoneRequest(phone))
        }.body()
    }

    suspend fun sendSmsSignUp(
        phone: String,
        code: String,
        firstName: String,
        lastName: String
    ): AuthorizationResponse {
        return client.post("/cleaners/self/reg/sms-check") {
            setBody(SignUpRequest(phone, code, firstName, lastName))
        }.body()
    }
}