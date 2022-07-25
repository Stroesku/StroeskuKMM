package ru.stroesku.kmm.domain.di

import org.koin.dsl.module
import ru.stroesku.kmm.data.repositories.UserRepository

object RepositoryModule {

    fun get() = module {
        single { UserRepository(get(), get(),get()) }
    }
}