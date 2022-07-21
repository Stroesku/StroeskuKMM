package ru.stroesku.kmm.domain.di

import ru.stroesku.kmm.data.local.TokenPreferences
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.stroesku.kmm.domain.exception.factory.ApsExceptionFactory.parseKtorClientExceptionToDtkException
import timber.log.Timber

@ExperimentalSerializationApi
object ClientModule {

    private const val HOST_PATH = "server_host"
    private const val ONE_MIN = 60_000

    fun get() = module {
        single { CoroutineScope(SupervisorJob()) + CoroutineExceptionHandler { _, e -> Timber.e("CoroutineExceptionHandler catch -> $e") } }
        single { TokenPreferences(androidApplication(), get()) }
        single { buildClient(getProperty(HOST_PATH), get()) }
    }

    private fun buildClient(serverHost: String, tokenPreferences: TokenPreferences): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    val clientException = cause as? ClientRequestException
                        ?: return@handleResponseExceptionWithRequest

                    throw  parseKtorClientExceptionToDtkException(clientException)
                }
            }
            engine {
                connectTimeout = ONE_MIN
                socketTimeout = ONE_MIN
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.i(message)
                    }
                }

                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                url { protocol = URLProtocol.HTTPS }
                host = serverHost
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    encodeDefaults = true
                })
            }

        }.apply {
            plugin(HttpSend).intercept { request ->
                val token = tokenPreferences.getCurrentToken()
                if (token.isNotEmpty()) {
                    request.headers.append(HttpHeaders.Authorization, token)
                }
                execute(request)
            }
        }
    }
}
