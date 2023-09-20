package com.example.kotlin.external.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.example.kotlin.utils.bodyTo
import com.example.kotlin.controller.InformationDto
import com.example.kotlin.utils.getValidResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

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
                .getValidResponse()
                .bodyTo(ProcessingResult::class.java)
    }
}
