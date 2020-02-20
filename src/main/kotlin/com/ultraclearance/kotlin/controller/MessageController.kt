package com.ultraclearance.kotlin.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/message")
class MessageController {

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: String): String {
        return "message was sent!"
    }
}
