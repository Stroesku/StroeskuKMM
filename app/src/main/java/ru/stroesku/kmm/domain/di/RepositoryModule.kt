package ru.stroesku.kmm.domain.di

import ru.stroesku.kmm.data.repositories.UserRepository
import org.koin.dsl.module

object RepositoryModule {

    fun get() = module {
        single { UserRepository(get(), get()) }
    }
}