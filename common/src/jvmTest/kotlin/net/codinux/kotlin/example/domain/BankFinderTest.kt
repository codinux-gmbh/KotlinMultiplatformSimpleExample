package net.codinux.kotlin.example.domain

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BankFinderTest {

  private val underTest = BankFinder()


  @Test
  fun byNameAndCity() {
    val result = underTest.findBanks("Bundesbank Berlin")

    assertEquals(1, result.size)

    val bundesbank = result.first()
    assertEquals("10000000", bundesbank.bankCode)
    assertEquals("MARKDEF1100", bundesbank.bic)
    assertEquals("10591", bundesbank.postalCode)
  }

  @Test
  fun byCity() {
    val result = underTest.findBanks("Hamburg")

    assertEquals(92, result.size)
  }

  @Test
  fun byName() {
    val result = underTest.findBanks("Sparkasse")

    assertEquals(478, result.size)
  }

  @Test
  fun umlauts() {
    val result = underTest.findBanks("Köln")

    assertEquals(59, result.size)
  }

}