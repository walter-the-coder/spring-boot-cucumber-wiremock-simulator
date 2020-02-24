package com.ultraclearance.kotlin.external.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.ultraclearance.kotlin.controller.InformationDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.lang.NullPointerException

@Component
class InformationProcessorClient(
        @Value("\${external.informationProcessor.url}")
        private val baseUrl: String,
        private val objectMapper: ObjectMapper
) {

    private val client = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

    fun processInformation(informationDto: InformationDto): ProcessingResult {
        return client.post().uri("/whatever")
                .bodyValue(objectMapper.writeValueAsString(informationDto))
                .exchange()
                .block()
                ?.bodyToMono(ProcessingResult::class.java)
                ?.block() ?: throw NullPointerException("Failed to get result from the information processor!")
    }
}