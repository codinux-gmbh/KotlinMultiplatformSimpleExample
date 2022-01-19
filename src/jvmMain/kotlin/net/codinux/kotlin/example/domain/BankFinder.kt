package net.codinux.kotlin.example.domain

import net.codinux.kotlin.example.domain.model.BankInfo
import net.dankito.utils.serialization.JacksonJsonSerializer
import org.slf4j.LoggerFactory


class BankFinder {

  companion object {
    const val BankListFileName = "BankList.json"

    private val log = LoggerFactory.getLogger(BankFinder::class.java)
  }


  private val banks = loadBankList()


  fun findBanks(query: String): List<BankInfo> {
    if (query.isNullOrBlank()) {
      return banks
    }

    if (query.toIntOrNull() != null) { // if query is an integer, then it can only be a bank code, but not a bank name or city
      return findBankByBankCode(query)
    }

    return findBankByNameOrCity(query)
  }

  private fun findBankByBankCode(query: String): List<BankInfo> {
    if (query.isEmpty()) {
      return banks
    }

    return banks.filter { it.bankCode.startsWith(query) }
  }

  private fun findBankByNameOrCity(query: String): List<BankInfo> {
    val queryPartsLowerCase = query.toLowerCase().split(" ", "-")

    return banks.filter { bankInfo ->
      matchAllPartsBankNameOrCity(queryPartsLowerCase, bankInfo)
    }
  }

  private fun matchAllPartsBankNameOrCity(queryPartsLowerCase: List<String>, bankInfo: BankInfo): Boolean {
    for (queryPartLowerCase in queryPartsLowerCase) {
      if (matchesBankNameOrCity(bankInfo, queryPartLowerCase) == false) {
        return false
      }
    }

    return true
  }

  private fun matchesBankNameOrCity(bankInfo: BankInfo, queryLowerCase: String): Boolean {
    return bankInfo.name.toLowerCase().contains(queryLowerCase)
        || bankInfo.city.toLowerCase().startsWith(queryLowerCase)
        || bankInfo.branchesInOtherCities.any { it.toLowerCase().startsWith(queryLowerCase) }
  }


  private fun loadBankList(): List<BankInfo> {
    try {
      val bankListString = readBankListFile()

      JacksonJsonSerializer().deserializeList(bankListString, BankInfo::class.java)?.let {
        return it
      }
    } catch (e: Exception) {
      log.error("Could not load bank list", e)
    }

    return listOf()
  }

  private fun readBankListFile(): String {
    val inputStream = BankFinder::class.java.classLoader.getResourceAsStream(BankListFileName)

    return inputStream.bufferedReader().readText()
  }

}