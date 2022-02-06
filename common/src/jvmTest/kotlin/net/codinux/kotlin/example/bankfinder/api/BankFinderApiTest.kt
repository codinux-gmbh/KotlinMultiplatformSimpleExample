package net.codinux.kotlin.example.bankfinder.api

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import net.codinux.kotlin.example.bankfinder.api.configureBankFinderRouting
import net.codinux.kotlin.example.configureHTTP
import net.codinux.kotlin.example.configureSerialization
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderUrlConfig.Companion.BankFinderFindBanksQueryQueryParameter
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderUrlConfig.Companion.BankFinderPath

class BankFinderApiTest {

    @Test
    fun queryBerlin() {
        withTestApplication({ configureBankFinderRouting(); configureSerialization(); configureHTTP() }) {
            handleRequest(HttpMethod.Get, "$BankFinderPath?$BankFinderFindBanksQueryQueryParameter=berlin").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("application/json; charset=UTF-8", response.contentType().toString())
                assertEquals(22173, response.byteContent?.size)
            }
        }
    }

}