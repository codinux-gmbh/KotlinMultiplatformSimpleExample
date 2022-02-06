package net.codinux.kotlin.example.utils.web


interface IWebClient {

    suspend fun get(param: RequestParam): WebClientResponse

}