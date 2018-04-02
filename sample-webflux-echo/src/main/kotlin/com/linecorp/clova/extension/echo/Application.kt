package com.linecorp.clova.extension.echo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class EchoApplication

fun main(args: Array<String>) {
    runApplication<EchoApplication>(*args)
}
