package net.codinux.kotlin.example.favicon.model

import kotlinx.serialization.Serializable


@Serializable
class Favicon(
    val url : String,
    val iconType : FaviconType,
    val size : Size? = null,
    val mimeType: String? = null
) {

    constructor() : this("", FaviconType.Icon, null, null) // for object deserializers


    override fun toString(): String {
        return "$iconType $size $url"
    }

}