package net.codinux.kotlin.example.domain.service

import net.codinux.kotlin.example.domain.dataaccess.BankFinderClient
import net.codinux.kotlin.example.domain.dataaccess.BankFinderUrlConfig
import net.codinux.kotlin.example.domain.model.BankInfo

class BankFinderService(
  host: String = BankFinderUrlConfig.Host,
  port: Int = BankFinderUrlConfig.Port
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