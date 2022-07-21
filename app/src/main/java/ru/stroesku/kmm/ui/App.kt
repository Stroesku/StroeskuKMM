package ru.stroesku.kmm.ui

import android.app.Application
import ru.stroesku.kmm.domain.di.AppModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.fileProperties
import ru.stroesku.kmm.DtkTree
import timber.log.Timber

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@FlowPreview
@KoinExperimentalAPI
@ObsoleteCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DtkTree())
        startKoin {
            androidLogger()
            fileProperties()
            androidContext(this@App)
            modules(AppModules.get())
        }
    }
}