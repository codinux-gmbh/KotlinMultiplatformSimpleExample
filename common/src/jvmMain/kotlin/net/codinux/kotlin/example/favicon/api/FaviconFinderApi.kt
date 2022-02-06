package net.codinux.kotlin.example.favicon.api

import com.soywiz.korim.format.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderUrlConfig.Companion.FaviconFinderPath
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderUrlConfig.Companion.FaviconFinderUrlQueryParameter
import net.codinux.kotlin.example.favicon.domain.FaviconService
import net.codinux.kotlin.example.favicon.model.Favicon


private val api = FaviconFinderApi()

fun Application.configureFaviconFinderRouting() {

  routing {
    get(FaviconFinderPath) {
      try {
        val url = call.request.queryParameters[FaviconFinderUrlQueryParameter] ?: ""

        if (url.isNullOrBlank() == false) {
          call.respond(api.extractFavicons(url))
        } else {
          call.respond(listOf<Favicon>())
        }
      } catch (e: Exception) {
        log.error("Could not handle request for $FaviconFinderPath", e)
      }
    }
  }

}


class FaviconFinderApi {

  private val faviconService = FaviconService()


  fun extractFavicons(url: String): List<Favicon> {
    return faviconService.extractFavicons(url)
  }

}