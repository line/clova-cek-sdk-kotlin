package com.linecorp.clova.extension.echo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class EchoMvcApplication

fun main(args: Array<String>) {
    runApplication<EchoMvcApplication>(*args)
}
