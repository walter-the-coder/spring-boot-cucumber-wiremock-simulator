package com.ultraclearance.kotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ultraclearance.kotlin.external.clients.InformationProcessorClient
import com.ultraclearance.kotlin.external.clients.ProcessingResult
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [InformationControllerITest.InformationControllerMockBeans::class])
class InformationControllerITest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @TestConfiguration
    class InformationControllerMockBeans {

        @Bean
        fun informationProcessorClient(): InformationProcessorClient {
            return informationProcessorClient
        }
    }

    @Test
    fun testInformationController() {
        val informationDto = InformationDto(details = "some details")
        mockMvc.perform(post("/api/information/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(informationDto)))
                .andExpect(status().is2xxSuccessful)
                .andExpect(content().string("information was received!"))
                .andReturn()
    }

    @Test
    fun submitAndProcessInformation() {
        val informationDto = InformationDto(details = "some details")
        val processingResult = ProcessingResult("DONE", true)
        every {
            informationProcessorClient.processInformation(any())
        } returns processingResult

        mockMvc.perform(post("/api/information/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(informationDto)))
                .andExpect(status().is2xxSuccessful)
                .andExpect {
                    val reponseDto = objectMapper.readValue(it.response.contentAsString, ResponseDto::class.java)
                    assert(reponseDto.message == "Information processed with status=DONE")
                }
                .andReturn()
    }

    private companion object {
        val informationProcessorClient = mockk<InformationProcessorClient>()
    }
}
