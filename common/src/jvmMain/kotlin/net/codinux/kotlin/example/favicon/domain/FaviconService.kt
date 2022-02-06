package net.codinux.kotlin.example.favicon.domain

import kotlinx.coroutines.runBlocking
import net.codinux.kotlin.example.favicon.model.Favicon
import net.codinux.kotlin.example.favicon.model.FaviconType
import net.codinux.kotlin.example.favicon.model.Size
import net.dankito.utils.favicon.FaviconFinder


class FaviconService {

    private val faviconFinder = FaviconFinder()

    private val imageService = ImageService()

    private val mimeTypeService = MimeTypeService()


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
            return@runBlocking favicons.map { getImageSize(it) }
        }
    }

    private suspend fun getImageSize(favicon: Favicon): Favicon {
        if (favicon.size == null && favicon.mimeType.isNullOrBlank() == false) {
            imageService.getImageSize(favicon.url, favicon.mimeType)?.let { size ->
                return Favicon(favicon.url, favicon.iconType, size, favicon.mimeType)
            }
        }

        return favicon
    }

    private fun setMimeTypeIfNotSet(favicon: Favicon): Favicon {
        if (favicon.mimeType.isNullOrBlank()) {
            mimeTypeService.getMimeType(favicon.url)?.let { mimeType ->
                return Favicon(favicon.url, favicon.iconType, favicon.size, mimeType)
            }
        }

        return favicon
    }

    private fun makeUrlAbsolute(url: String): String {
        var adjustedUrl = url

        if (adjustedUrl.startsWith("http") == false) {
            adjustedUrl = "https://$url"
        }

        return adjustedUrl
    }

}