package net.codinux.kotlin.example.favicon.domain

import com.soywiz.korim.format.*
import com.soywiz.korim.format.jpg.JPEG
import com.soywiz.korio.file.std.MemoryVfs
import com.soywiz.korio.stream.openAsync
import net.codinux.kotlin.example.favicon.model.Size
import net.codinux.kotlin.example.utils.web.IWebClient
import net.codinux.kotlin.example.utils.web.KtorWebClient
import net.codinux.kotlin.example.utils.web.RequestParam
import net.codinux.kotlin.example.utils.web.ResponseType
import org.slf4j.LoggerFactory


class ImageService {

    private val webClient: IWebClient = KtorWebClient()

    private val log = LoggerFactory.getLogger(ImageService::class.java)


    init {
        RegisteredImageFormats.register(PNG, ICO, JPEG, SVG)
    }


    suspend fun getImageSize(imageUrl: String, imageMimeType: String): Size? {
        try {
            webClient.get(RequestParam(imageUrl, ResponseType.ByteArray)).response?.let { iconBytes ->
                val imageData = MemoryVfs(mapOf(imageMimeType to iconBytes.openAsync()))[imageMimeType].readImageData()
                if (imageData.width > 0 && imageData.height > 0) {
                    return Size(imageData.width, imageData.height)
                }
            }
        } catch (e: Exception) {
            log.error("Could not determine size of image $imageUrl", e)
        }

        return null
    }

}