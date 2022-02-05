package net.codinux.kotlin.example.bankfinder.api

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import net.codinux.kotlin.example.bankfinder.domain.BankFinder
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderUrlConfig.Companion.BankFinderFindBanksQueryQueryParameter
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderUrlConfig.Companion.BankFinderPath


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