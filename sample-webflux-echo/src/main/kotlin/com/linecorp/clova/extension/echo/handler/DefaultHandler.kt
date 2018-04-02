/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.echo.handler

import com.linecorp.clova.extension.client.ClovaClient
import com.linecorp.clova.extension.client.clovaClient
import com.linecorp.clova.extension.client.intentHandler
import com.linecorp.clova.extension.client.launchHandler
import com.linecorp.clova.extension.client.sessionEndedHandler
import com.linecorp.clova.extension.converter.jackson.JacksonObjectMapper
import com.linecorp.clova.extension.model.util.simpleResponse
import com.linecorp.clova.extension.model.util.simpleResponseWithReprompt
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.reactor.asMono
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DefaultHandler {
    private val logger: Logger = LoggerFactory.getLogger("clova-extension")

    //The applicationId you registered in Clova Developer Center.
    val client: ClovaClient = clovaClient(applicationId = "idv.freddie.example") {
        objectMapper = JacksonObjectMapper()
        launchHandler { _, _ ->
            simpleResponse(message = "はい、エクステンションが起動しました")
        }

        intentHandler { request, session ->
            val value = request.intent.slots["number"]?.value
            simpleResponseWithReprompt(
                    message = "数字は${value}です",
                    repromptMessage = "もう一度話しますか")
        }

        sessionEndedHandler { _, _ ->
            simpleResponse(message = "またね")
        }
    }

    fun healthCheck(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(fromObject("ok"))

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        logger.info("The headers: ${request.headers()}")
        return request
                .bodyToMono(String::class.java)
                //transform request body to response body
                //transform response body to server response
                .flatMap { requestBody ->
                    logger.info("<- response: $requestBody")
                    val responseBody = client.handleClovaRequest(requestBody, request.headers().asHttpHeaders())
                    ServerResponse.ok().body(fromObject(responseBody))
                }
    }
}
