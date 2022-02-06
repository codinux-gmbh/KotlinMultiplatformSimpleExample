package net.codinux.kotlin.example.utils.web


class WebClientResponse(
    val successful: Boolean,
    val responseCode: Int,
    val body: String? = null,
    val response: ByteArray? = null,
    val error: Exception? = null
) {

    override fun toString(): String {
        return if (successful) {
            "Successful: $responseCode"
        } else {
            "Error: $error"
        }
    }

}