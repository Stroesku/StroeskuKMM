package ru.stroesku.kmm.data.repositories

import ru.stroesku.kmm.data.api.UserApi
import ru.stroesku.kmm.data.local.TokenPreferences
import ru.stroesku.kmm.domain.param.UserInfoParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val api: UserApi,
    private val tokenPreferences: TokenPreferences,
) {
    fun sendPhoneSignIn(phone: String): Flow<Any> {
        return flow { emit(api.sendPhoneSignIn(phone)) }
    }

    fun sendSmsCodeSignIn(param: UserInfoParam): Flow<Any> {
        return flow {
            emit(
                api.sendSmsSignIn(
                    param.phone.orEmpty(),
                    param.code.orEmpty()
                )
            )
        }.map { tokenPreferences.setToken(it.tokenType, it.token) }
    }

    fun sendPhoneSignUp(phone: String): Flow<Any> {
        return flow { emit(api.sendPhoneSignUp(phone)) }
    }

    fun sendSmsCodeSignUp(param: UserInfoParam): Flow<Any> {
        val phone = param.phone.orEmpty()
        val code = param.code.orEmpty()
        val firstName = param.firstName.orEmpty()
        val lastName = param.lastName.orEmpty()
        return flow {
            emit(
                api.sendSmsSignUp(
                    phone, code, firstName, lastName
                )
            )
        }.map {
            tokenPreferences.setToken(it.tokenType, it.token)
        }
    }

}