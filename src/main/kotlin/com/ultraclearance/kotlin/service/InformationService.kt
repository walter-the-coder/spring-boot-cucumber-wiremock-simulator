package com.example.kotlin.service

import com.example.kotlin.controller.InformationDto
import com.example.kotlin.controller.ResponseDto
import com.example.kotlin.external.clients.InformationProcessorClient
import org.springframework.stereotype.Service

@Service
class InformationService(private val informationProcessorClient: InformationProcessorClient) {

    fun handleInformation(informationDto: InformationDto): ResponseDto {
        val processingResult = informationProcessorClient.processInformation(informationDto)

        return ResponseDto(
                message = "Information processed with status=${processingResult.status}"
        )
    }
}
