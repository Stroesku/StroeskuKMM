package ru.stroesku.kmm.data.local

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore

abstract class BasePreferences(private val key: String, val context: Context) {

    protected fun getDataStore() = context.dataStore

    val Context.dataStore by preferencesDataStore(
        name = key,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context, key))
        })
}