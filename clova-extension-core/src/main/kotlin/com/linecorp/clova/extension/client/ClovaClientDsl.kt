package com.linecorp.clova.extension.client

import com.linecorp.clova.extension.Dsl
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.SessionEndedRequest

/**
 * The definition of DSL keyword clovaClient.
 *
 * The syntax is as follows:
 * ```
 *   val clovaClient = clovaClient {
 *       objectMapper = JacksonObjectMapper()
 *       launchHandler { intentRequest, session ->
 *           simpleResponse("Hello, Clova!")
 *       }
 *       intentHandler { intentRequest, session ->
 *           simpleResponse("What can I do?")
 *       }
 *   }
 * ```
 */
@Dsl
fun clovaClient(
        applicationId: String,
        verifier: RequestVerifier = RequestVerifierImpl(),
        init: ClovaClient.() -> Unit): ClovaClient {
    val client = ClovaClient(applicationId, verifier)
    client.init()
    return client
}

/**
 * The definition of DSL keyword launchHandler
 *
 * @param handler The user-defined launch handler
 */
@Dsl
fun ClovaClient.launchHandler(handler: RequestHandler<LaunchRequest>) {
    launchHandler = handler
}

/**
 * The definition of DSL keyword intentHandler
 *
 * @param handler The user-defined intent handler
 */
@Dsl
fun ClovaClient.intentHandler(handler: RequestHandler<IntentRequest>) {
    intentHandler = handler
}

/**
 * The definition of DSL keyword sessionEndedHandler
 *
 * @param handler the user-defined session end handler
 */
@Dsl
fun ClovaClient.sessionEndedHandler(handler: RequestHandler<SessionEndedRequest>) {
    sessionEndedHandler = handler
}
