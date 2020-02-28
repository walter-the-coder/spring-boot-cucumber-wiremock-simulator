package features.simulators

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.ResponseDefinition
import com.ultraclearance.kotlin.controller.InformationDto
import com.ultraclearance.kotlin.external.clients.ProcessingResult
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object InformationProcessorSimulator : Simulator(9001, "InformationProcessorSimulator") {
    init {
        postMappings = mapOf("/whatever" to ::processInformation)
    }

    fun processInformation(request: Request): ResponseDefinition {

        val informationDto: InformationDto = objectMapper.readValue(request.body, InformationDto::class.java)
        val prosessingResult = mockProcessingResult(informationDto)

        return ResponseDefinitionBuilder()
                .withBody(objectMapper.writeValueAsBytes(prosessingResult))
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withStatus(HttpStatus.OK.value())
                .build()
    }

    private fun mockProcessingResult(informationDto: InformationDto): ProcessingResult {
        return if (informationDto.details ==
                "this information should be processed externally and end up with status OK") {
            ProcessingResult(status = "OK", processed = true)
        } else {
            ProcessingResult(status = "FAULTY", processed = true)
        }
    }
}
