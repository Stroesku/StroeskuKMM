package ru.stroesku.kmm.domain.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module

@ExperimentalSerializationApi
@ObsoleteCoroutinesApi
@KoinExperimentalAPI
@FlowPreview
@ExperimentalCoroutinesApi
object AppModules {

    fun get(): List<Module> = listOf(
        ClientModule.get(),
        ApiModule.get(),
        MapperModule.get(),
        RepositoryModule.get()
    )
}