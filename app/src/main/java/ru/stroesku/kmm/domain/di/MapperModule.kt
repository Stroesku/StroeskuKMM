package ru.stroesku.kmm.domain.di

import org.koin.dsl.module
import ru.stroesku.kmm.domain.mappers.ProfileMapper

object MapperModule {
    fun get() = module {
        single { ProfileMapper() }
    }
}