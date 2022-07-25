package ru.stroesku.kmm.data.api



import ru.stroesku.kmm.data.request.PhoneAndCodeRequest
import ru.stroesku.kmm.data.request.SignUpRequest
import ru.stroesku.kmm.data.request.PhoneRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import ru.stroesku.kmm.data.dto.AuthorizationResponse
import ru.stroesku.kmm.data.dto.ProfileDto
import java.io.File

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

    suspend fun getProfile(): ProfileDto {
        return client.get("cleaners/self/detail").body()
    }

    /**
     * Update
     */
    suspend fun updateProfile(
        firstName: String? = null,
        lastName: String? = null,
        birthDate: String? = null,
        email: String? = null,
        file: File? = null
    ): ProfileDto {
        return client.post("/cleaners/self/update-profile") {
            setBody(MultiPartFormDataContent(formData {
                firstName?.let { append("first_name", it) }
                lastName?.let { append("last_name", it) }
                birthDate?.let { append("birth_date", it) }
                email?.let { append("email", it) }
                file?.readBytes()?.let {
                    append("picture", it, Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            }))

        }.body()
    }

    suspend fun sendPhoneUpdatePhone(phone: String): Any {
        return client.post("/cleaners/self/update-phone/sms-send") {
            setBody(PhoneRequest(phone))
        }.body()
    }

    suspend fun sendSmsUpdatePhone(phone: String, code: String): ProfileDto {
        return client.post("/cleaners/self/update-phone/sms-check") {
            setBody(PhoneAndCodeRequest(phone, code))
        }.body()
    }
}