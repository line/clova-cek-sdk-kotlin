# Clova extension sample
This is an echo example for clova extension. It would return the number that users speak in IntentRequest.

It is using Kotlin + SpringBoot2 + WebFlux.
The basic implementation is as follows.
```
@Component
class DefaultHandler {

    val client: ClovaClient = clovaClient {
        objectMapper = JacksonObjectMapper()
        launchHandler { _, _ ->
            //Implement your launch handler here
        }

        intentHandler { request, _ ->
            //Implement your intent handler here
        }

        sessionEndedHandler { _, _ ->
            simpleResponse(message = "またね")
        }
    }

    fun healthCheck(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(fromObject("ok"))

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        return request
                .bodyToMono(String::class.java)
                .flatMap { requestBody ->
                    logger.info("-> $requestBody\n")
                    val response = client.handleClovaRequest(
                            requestBody, getRequestSignature(request))
                    logger.info("<- $response\n")
                    return@flatMap ServerResponse.ok().body(
                            fromObject(response))
                }
    }

```
