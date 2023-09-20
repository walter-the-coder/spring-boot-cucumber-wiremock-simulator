package com.example.kotlin.controller

import com.example.kotlin.service.InformationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/information")
class InformationController(private val informationService: InformationService) {

    @PostMapping("/test")
    fun test(@RequestBody information: InformationDto): String {
        return "information was received!"
    }

    @PostMapping("/submit")
    fun submitInformation(@RequestBody information: InformationDto): ResponseDto {
        return informationService.handleInformation(information)
    }
}
