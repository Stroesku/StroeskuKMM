package ru.stroesku.kmm.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.stroesku.kmm.data.api.UserApi
import ru.stroesku.kmm.data.local.TokenPreferences
import ru.stroesku.kmm.domain.mappers.ProfileMapper
import ru.stroesku.kmm.domain.model.ProfileModel
import ru.stroesku.kmm.domain.param.ProfileUpdParam
import ru.stroesku.kmm.domain.param.SignParam

class UserRepository(
    private val api: UserApi,
    private val tokenPreferences: TokenPreferences,
    private val profileMapper: ProfileMapper
) {
    /**
     * Sign In
     */
    fun sendPhoneSignIn(phone: String): Flow<Any> {
        return flow { emit(api.sendPhoneSignIn(phone)) }
    }

    fun sendSmsCodeSignIn(param: SignParam): Flow<Any> {
        return flow {
            emit(
                api.sendSmsSignIn(
                    param.phone.orEmpty(),
                    param.code.orEmpty()
                )
            )
        }.map { tokenPreferences.setToken(it.tokenType, it.token) }
    }

    /**
     * Sign Up
     */
    fun sendPhoneSignUp(phone: String): Flow<Any> {
        return flow { emit(api.sendPhoneSignUp(phone)) }
    }

    fun sendSmsCodeSignUp(param: SignParam): Flow<Any> {
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


    fun getProfile(): Flow<ProfileModel> =
        flow { emit(api.getProfile()) }.map { profileMapper.map(it) }


    /**
     * Update
     */
    fun updateProfile(param: ProfileUpdParam): Flow<ProfileModel> {
        with(param) {
            return flow {
                emit(
                    api.updateProfile(
                        firstName,
                        lastname,
                        dateOfBirth,
                        email,
                        photo
                    )
                )
            }.map { profileMapper.map(it) }
        }
    }

    fun sendPhoneUpdatePhone(phone: String): Flow<Any> {
        return flow { emit(api.sendPhoneUpdatePhone(phone)) }
    }

    fun sendSmsCodeUpdatePhone(param: SignParam): Flow<Any> {
        return flow {
            emit(
                api.sendSmsUpdatePhone(
                    param.phone.orEmpty(),
                    param.code.orEmpty()
                )
            )
        }
    }

}