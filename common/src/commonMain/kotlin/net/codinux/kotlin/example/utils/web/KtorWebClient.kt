package net.codinux.kotlin.example.utils.web

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText


class KtorWebClient : IWebClient {

    private val client = HttpClient {
        install(ContentNegotiation) {

        }
    }


    override suspend fun get(param: RequestParam): WebClientResponse {
        try {
            val response = client.get(param.url)

            if (response.status.value in 200..299) {
                return WebClientResponse(true, response.status.value,
                    if (param.responseType == ResponseType.String) response.bodyAsText() else null,
                    if (param.responseType == ResponseType.ByteArray) response.body() else null)
            }

            return WebClientResponse(false, response.status.value)
        } catch (e: Exception) {
            return WebClientResponse(false, -1, error = e)
        }
    }

}