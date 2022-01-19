package net.codinux.kotlin.example.api

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import net.codinux.kotlin.example.domain.BankFinder

const val BankFinderPath = "/bankfinder"

const val BankFinderFindBanksQueryQueryParameter = "query"


fun Application.configureBankFinderRouting() {

  val bankFinder = BankFinder()

  routing {
    get(BankFinderPath) {
      try {
        val query = call.request.queryParameters[BankFinderFindBanksQueryQueryParameter] ?: ""

        call.respond(bankFinder.findBanks(query))
      } catch (e: Exception) {
        log.error("Could not handle request for $BankFinderPath", e)
      }
    }
  }

}