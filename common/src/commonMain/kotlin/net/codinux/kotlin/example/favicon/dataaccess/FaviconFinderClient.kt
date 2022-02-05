package net.codinux.kotlin.example.favicon.dataaccess

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.codinux.kotlin.example.ServerConfig
import net.codinux.kotlin.example.favicon.model.Favicon


class FaviconFinderClient(
  val host: String = ServerConfig.Host,
  val port: Int = ServerConfig.Port
) {

  private val client = HttpClient {
    install(ContentNegotiation) {

    }
  }

  private val format = Json { ignoreUnknownKeys = true }


  suspend fun extractFavicons(url: String): List<Favicon> {
    val response = client.get("http://$host:$port${FaviconFinderUrlConfig.FaviconFinderPath}") {
      parameter(FaviconFinderUrlConfig.FaviconFinderUrlQueryParameter, url)
    }

    if (response.status.value == 200) {
      return format.decodeFromString(response.bodyAsText())
    }

    return listOf() // TODO: throw exception?
  }
}