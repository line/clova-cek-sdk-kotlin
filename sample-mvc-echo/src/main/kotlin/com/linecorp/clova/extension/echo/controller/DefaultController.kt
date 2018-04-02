/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.echo.controller

import com.linecorp.clova.extension.client.ClovaClient
import com.linecorp.clova.extension.client.clovaClient
import com.linecorp.clova.extension.client.intentHandler
import com.linecorp.clova.extension.client.launchHandler
import com.linecorp.clova.extension.client.sessionEndedHandler
import com.linecorp.clova.extension.converter.jackson.JacksonObjectMapper
import com.linecorp.clova.extension.model.util.simpleResponse
import com.linecorp.clova.extension.model.util.simpleResponseWithReprompt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class DefaultController {
    private val logger: Logger = LoggerFactory.getLogger("clova-extension")

    val client: ClovaClient = clovaClient(applicationId = "idv.freddie.example") {
        objectMapper = JacksonObjectMapper()
        launchHandler { _, _ ->
            simpleResponse(message = "はい、エクステンションが起動しました")
        }

        intentHandler { request, _ ->
            val value = request.intent.slots["number"]?.value
            simpleResponseWithReprompt(
                    message = "数字は${value}です",
                    repromptMessage = "もう一度話しますか")
        }

        sessionEndedHandler { _, _ ->
            simpleResponse(message = "またね")
        }
    }

    @GetMapping
    fun healthCheck(): String = "ok"

    @PostMapping("/echo")
    fun echo(@RequestBody body: String, @RequestHeader headers: HttpHeaders): String {
        logger.info("The headers: $headers")
        return client.handleClovaRequest(body, headers)
    }
}
