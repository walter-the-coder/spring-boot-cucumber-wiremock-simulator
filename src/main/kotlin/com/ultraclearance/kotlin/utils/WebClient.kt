package com.example.kotlin.utils

import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient

fun WebClient.RequestHeadersSpec<*>.getValidResponse(): ClientResponse {
    return this.exchange()
            .block() ?: throw NullPointerException("We did not get a valid response from the application!")
}

fun <T : Any> ClientResponse.bodyTo(clazz: Class<T>): T {
    return this.bodyToMono(clazz).block()
            ?: throw RuntimeException("Could not transform response body to ${clazz.simpleName}!")
}
