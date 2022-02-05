package net.codinux.kotlin.example.bankfinder.service

import net.codinux.kotlin.example.ServerConfig
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderClient
import net.codinux.kotlin.example.bankfinder.model.BankInfo

class BankFinderService(
  host: String = ServerConfig.Host,
  port: Int = ServerConfig.Port
) {

  private val client = BankFinderClient(host, port)


  suspend fun findAndLogBanks(query: String) {
    findBanks(query).forEachIndexed { index, bank ->
      println("[$index] ${bank.bankCode} ${bank.name} ${bank.city}")
    }
  }

  suspend fun findBanks(query: String): List<BankInfo> {
    return client.findBanks(query)
  }

}