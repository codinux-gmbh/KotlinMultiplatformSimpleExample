package net.codinux.kotlin.example.favicon.service

import net.codinux.kotlin.example.ServerConfig
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderClient
import net.codinux.kotlin.example.favicon.model.Favicon

class FaviconFinderService(host: String, port: Int) { // Swift doesn't support default parameters

  private val client = FaviconFinderClient(host, port)


  constructor() : this(ServerConfig.Host, ServerConfig.Port)


  suspend fun extractAndLogFavicons(url: String) {
    extractFavicons(url).forEachIndexed { index, favicon ->
      println("[$index] ${favicon.size} ${favicon.iconType} ${favicon.url}")
    }
  }

  suspend fun extractFavicons(url: String): List<Favicon> {
    return client.extractFavicons(url)
  }

}