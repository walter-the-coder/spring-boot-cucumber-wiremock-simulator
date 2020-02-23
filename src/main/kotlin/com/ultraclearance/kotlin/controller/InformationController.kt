package com.ultraclearance.kotlin.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/information")
class InformationController {

    @PostMapping("/store")
    fun storeInformation(@RequestBody information: InformationDto): String {
        return "information was stored!"
    }
}
