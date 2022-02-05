package net.codinux.kotlin.example.favicon.api

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderUrlConfig.Companion.FaviconFinderPath
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderUrlConfig.Companion.FaviconFinderUrlQueryParameter
import net.codinux.kotlin.example.favicon.model.Favicon
import net.codinux.kotlin.example.favicon.model.FaviconType
import net.codinux.kotlin.example.favicon.model.Size
import net.dankito.utils.favicon.FaviconFinder


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

  private val faviconFinder = FaviconFinder()


  fun extractFavicons(url: String): List<Favicon> {
    var adjustedUrl = url
    if (adjustedUrl.startsWith("http") == false) {
      adjustedUrl = "https://$url"
    }

    return map(faviconFinder.extractFavicons(adjustedUrl)).sortedBy { it.size }
  }

  private fun map(favicons: List<net.dankito.utils.favicon.Favicon>): List<Favicon> {
    return favicons.map {
      Favicon(it.url, FaviconType.valueOf(it.iconType.name), it.size?.let { size -> Size(size.width, size.height) }, it.type)
    }
  }

}