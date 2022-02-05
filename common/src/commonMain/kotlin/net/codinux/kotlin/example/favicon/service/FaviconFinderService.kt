package net.codinux.kotlin.example.favicon.service

import net.codinux.kotlin.example.ServerConfig
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderClient
import net.codinux.kotlin.example.favicon.model.Favicon

class FaviconFinderService(
  host: String = ServerConfig.Host,
  port: Int = ServerConfig.Port
) {

  private val client = FaviconFinderClient(host, port)


  suspend fun extractAndLogFavicons(url: String) {
    extractFavicons(url).forEachIndexed { index, favicon ->
      println("[$index] ${favicon.size} ${favicon.iconType} ${favicon.url}")
    }
  }

  suspend fun extractFavicons(url: String): List<Favicon> {
    return client.extractFavicons(url)
  }

}