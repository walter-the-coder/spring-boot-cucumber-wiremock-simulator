package com.ultraclearance.kotlin.service

import com.ultraclearance.kotlin.controller.InformationDto
import com.ultraclearance.kotlin.controller.ResponseDto
import com.ultraclearance.kotlin.external.clients.InformationProcessorClient
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
