package net.codinux.kotlin.example.favicon.api

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationCall
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import net.codinux.kotlin.example.bankfinder.api.configureBankFinderRouting
import net.codinux.kotlin.example.bankfinder.dataaccess.BankFinderUrlConfig
import net.codinux.kotlin.example.configureHTTP
import net.codinux.kotlin.example.configureSerialization
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderUrlConfig
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FaviconFinderApiTest {

    @Test
    fun extractFaviconsForNewYorkTimes() {
        extractFavicons("nytimes.com", 1279)
    }

    @Test
    fun extractFaviconsForGuardian() {
        extractFavicons("theguardian.com", 1748)
    }

    @Test
    fun extractFaviconsForHeise() {
        extractFavicons("heise.de", 1090)
    }

    @Test
    fun extractFaviconsForSpiegel() { // spiegel contains a JPEG icon
        extractFavicons("spiegel.de", 1767)
    }


    private fun extractFavicons(url: String, expectedContentSize: Int): TestApplicationCall? {
        withTestApplication({ configureFaviconFinderRouting(); configureSerialization(); configureHTTP() }) {
            return@withTestApplication handleRequest(HttpMethod.Get, "${FaviconFinderUrlConfig.FaviconFinderPath}?${FaviconFinderUrlConfig.FaviconFinderUrlQueryParameter}=$url").apply {
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                kotlin.test.assertEquals("application/json; charset=UTF-8", response.contentType().toString())
                kotlin.test.assertEquals(expectedContentSize, response.byteContent?.size)
            }
        }

        return null
    }

}