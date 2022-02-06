package net.codinux.kotlin.example.favicon.domain

import com.soywiz.korim.format.*
import com.soywiz.korim.format.jpg.JPEG
import com.soywiz.korio.file.std.MemoryVfs
import com.soywiz.korio.stream.openAsync
import kotlinx.coroutines.runBlocking
import net.codinux.kotlin.example.favicon.api.FaviconFinderApi
import net.codinux.kotlin.example.favicon.model.Favicon
import net.codinux.kotlin.example.favicon.model.FaviconType
import net.codinux.kotlin.example.favicon.model.Size
import net.codinux.kotlin.example.utils.web.IWebClient
import net.codinux.kotlin.example.utils.web.KtorWebClient
import net.codinux.kotlin.example.utils.web.RequestParam
import net.codinux.kotlin.example.utils.web.ResponseType
import net.dankito.utils.favicon.FaviconFinder
import org.slf4j.LoggerFactory


class FaviconService {

    private val faviconFinder = FaviconFinder()

    private val webClient: IWebClient = KtorWebClient()

    private val log = LoggerFactory.getLogger(FaviconFinderApi::class.java)


    init {
        RegisteredImageFormats.register(PNG, ICO, JPEG, SVG)
    }


    fun extractFavicons(url: String): List<Favicon> {
        val adjustedUrl = makeUrlAbsolute(url)

        val result = map(faviconFinder.extractFavicons(adjustedUrl))
            .map { setMimeTypeIfNotSet(it) }

        return setImageSizeIfNotSet(result)
            .sortedBy { it.size }
    }

    private fun map(favicons: List<net.dankito.utils.favicon.Favicon>): List<Favicon> {
        return favicons.map {
            Favicon(it.url, FaviconType.valueOf(it.iconType.name), it.size?.let { size -> Size(size.width, size.height) }, it.type)
        }
    }

    private fun setImageSizeIfNotSet(favicons: List<Favicon>): List<Favicon> {
        return runBlocking {
            return@runBlocking favicons.map { findImageSize(it) }
        }
    }

    private suspend fun findImageSize(favicon: Favicon): Favicon {
        if (favicon.size != null || favicon.mimeType.isNullOrBlank()) {
            return favicon
        }

        try {
            webClient.get(RequestParam(favicon.url, ResponseType.ByteArray)).response?.let { iconBytes ->
                val imageData = MemoryVfs(mapOf(favicon.mimeType to iconBytes.openAsync()))[favicon.mimeType].readImageData()
                if (imageData.width > 0 && imageData.height > 0) {
                    return Favicon(favicon.url, favicon.iconType, Size(imageData.width, imageData.height), favicon.mimeType)
                }
            }
        } catch (e: Exception) {
            log.error("Could not determine size of Favicon $favicon", e)
        }

        return favicon
    }

    private fun setMimeTypeIfNotSet(favicon: Favicon): Favicon {
        if (favicon.mimeType.isNullOrBlank()) {
            return Favicon(favicon.url, favicon.iconType, favicon.size, getMimeType(favicon))
        }

        return favicon
    }

    private fun getMimeType(favicon: Favicon): String? {
        if (favicon.mimeType.isNullOrBlank() == false) {
            return favicon.mimeType
        }

        return when {
            favicon.url.endsWith(".png") -> "image/png"
            favicon.url.endsWith(".ico", true) -> "image/x-icon"
            favicon.url.endsWith(".jpg") || favicon.url.endsWith(".jpeg") -> "image/jpeg"
            favicon.url.endsWith(".svg") -> "image/svg+xml"
            else -> favicon.mimeType
        }
    }

    private fun makeUrlAbsolute(url: String): String {
        var adjustedUrl = url

        if (adjustedUrl.startsWith("http") == false) {
            adjustedUrl = "https://$url"
        }

        return adjustedUrl
    }

}