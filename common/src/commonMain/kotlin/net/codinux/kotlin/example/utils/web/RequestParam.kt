package net.codinux.kotlin.example.utils.web


class RequestParam(
    val url: String,
    val responseType: ResponseType
) {

    override fun toString(): String {
        return url
    }

}