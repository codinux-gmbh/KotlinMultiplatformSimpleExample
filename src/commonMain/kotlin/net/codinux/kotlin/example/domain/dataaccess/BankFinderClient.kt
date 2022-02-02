package net.codinux.kotlin.example.domain.dataaccess

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import net.codinux.kotlin.example.domain.model.BankInfo


class BankFinderClient {

  private val client = HttpClient {
    install(ContentNegotiation) {

    }
  }


  suspend fun findBanks(query: String): List<BankInfo> {
    val response = client.get("http://${BankFinderUrlConfig.Host}:${BankFinderUrlConfig.Port}${BankFinderUrlConfig.BankFinderPath}") {
      parameter(BankFinderUrlConfig.BankFinderFindBanksQueryQueryParameter, query)
    }

    if (response.status.value == 200) {
      return kotlinx.serialization.json.Json.decodeFromString(response.bodyAsText())
    }

    return listOf() // TODO: throw exception?
  }
}