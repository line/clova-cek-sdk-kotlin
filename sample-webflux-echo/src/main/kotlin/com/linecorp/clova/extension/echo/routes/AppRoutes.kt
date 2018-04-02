/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.echo.routes

import com.linecorp.clova.extension.echo.handler.DefaultHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class AppRoutes(private val handler: DefaultHandler) {

    @Bean
    fun default() = router {
        (accept(MediaType.APPLICATION_JSON) and "/").nest {
            //For healthy check of nucleo
            GET("/", handler::healthCheck)
            POST("/echo", handler::echo)
        }
    }
}
