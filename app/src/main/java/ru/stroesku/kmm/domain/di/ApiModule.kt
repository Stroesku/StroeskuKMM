package ru.stroesku.kmm.domain.di

import ru.stroesku.kmm.data.api.UserApi
import org.koin.dsl.module

object ApiModule {

    fun get() = module {
        single { UserApi(get()) }
    }
}