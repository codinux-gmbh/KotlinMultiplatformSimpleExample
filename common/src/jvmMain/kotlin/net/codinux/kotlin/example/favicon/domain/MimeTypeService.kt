package net.codinux.kotlin.example.favicon.domain

class MimeTypeService {

    fun getMimeType(urlOrFileName: String): String? {
        if (urlOrFileName.isNullOrBlank()) {
            return null
        }

        return when {
            urlOrFileName.endsWith(".png") -> "image/png"
            urlOrFileName.endsWith(".ico", true) -> "image/x-icon"
            urlOrFileName.endsWith(".jpg") || urlOrFileName.endsWith(".jpeg") -> "image/jpeg"
            urlOrFileName.endsWith(".svg") -> "image/svg+xml"
            else -> null
        }
    }

}