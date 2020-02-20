package com.ultraclearance.kotlin.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerITest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testSendMessage() {
        val message = "Hello"
        mockMvc.perform(post("/api/message/send").content(message))
                .andExpect(status().is2xxSuccessful)
                .andExpect(content().string("message was sent!"))
                .andReturn()
    }
}
