# Clova extension sample
This is an echo example for clova extension. It would return the number that users speak in IntentRequest.

It is using Kotlin + SpringBoot2 + WebMVC

The basic implementaion is as follows
```
@RestController
class DefaultController {

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

    @PostMapping("/echo")
    fun echo(@RequestBody body: String, @RequestHeader headers: HttpHeaders): String =
            client.handleClovaRequest(body, getRequestSignature(headers))
}
```