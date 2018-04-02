# The tutorial of creating Clova extension
This tutorial will introduce how to create the Clova extension with Kotlin SDK.
We will use SpringBoot2 and WebFlux for the server functions.

So let's start!

## Create the extension on Clova Developer Center
First we need to create the Clova extension on Clova Developer Center. Please refer to [creating channel](https://clova-developers.line.me/guide/#/DevConsole/Guides/CEK/Create_Channel.md) and [Build the Custom Extension](https://clova-developers.line.me/guide/#/CEK/Guides/Build_Custom_Extension.md)
to create the extension project first.

## Write the extension
Then to write the Clova extension, you can use Kotlin version of Clova Extension SDK. To setup SDK, please check [README.md](./README.md)

### Implement the extension url
In Spring framework 5, it supports Reactive Programming called WebFlux. It is easy to create Clova extension with WebFlux.

#### Add the routing path
First, create 2 classes ```DefaultHandler``` and ```AppRoutes```, add the implementation in ```AppRoutes``` as below
```
@Component
class AppRoutes(private val handler: DefaultHandler) {

    @Bean
    fun default() = router {
        (accept(MediaType.APPLICATION_JSON) and "/").nest {
            POST("/", handler::echo)
        }
    }
}
```
In the ```DefaultHandler```, the implementation as below

```
@Component
class DefaultHandler {
    val client: ClovaClient = clovaClient(applicationId = "YOUR APPLICATION ID") {
        launchHandler { request, session ->
            simpleResponse(message = "はい、エクステンションが起動しました")
        }

        intentHandler { request, session ->
            val value = request.intent.slots["number"]?.value
            simpleResponseWithReprompt(
                    message = "数字は${value}です",
                    repromptMessage = "もう一度話しますか")
        }

        sessionEndedHandler { request, session ->
            simpleResponse(message = "またね")
        }
    }

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        return request
                .bodyToMono(String::class.java)
                .flatMap { requestBody ->
                    val responseBody = client.handleClovaRequest(requestBody, request.headers().asHttpHeaders())
                    ServerResponse.ok().body(fromObject(responseBody))
                }
    }
}
```
Finally, create the ```main``` function like below
```
@SpringBootApplication
open class EchoApplication

fun main(args: Array<String>) {
    runApplication<EchoApplication>(*args)
}
```
Now we have a basic Clova extension.

### The request handlers
Currently, Kotlin SDK support 3 kinds of requests, described as follows
- **Launch Request**: It would be sent by Clova when users say the keyword to launch the extension. Usually it would be the first request in one conversation session.
- **Intent Request**: The main request from Clova, it woudl include the intent which is defined for the extension.
- **SessionEnded Reqeust**: It would be sent by Clova when users say some keywords to indicate Clova to stop this session. For example, users can say "止めろ" to terminate the conversation.

By previous section, we've created the conversation model for numbers, now we can implement the request handlers to echo the numbers that users say like this.
```
val client: ClovaClient = clovaClient(applicationId = "YOUR APPLICATION ID") {
        launchHandler { request, session ->
            simpleResponse(message = "はい、エクステンションが起動しました")
        }

        intentHandler { request, session ->
            val value = request.intent.slots["number"]?.value
            simpleResponseWithReprompt(
                    message = "数字は${value}です",
                    repromptMessage = "もう一度話しますか")
        }

        sessionEndedHandler { request, session ->
            simpleResponse(message = "またね")
        }
}
```

For more detail, please check official document https://clova-developers.line.me/guide/

## Run the extension
For development purpose, we can just use ```ngrok``` to redirect the url to local machine. It supports *https* and *http*, we can use it for testing extension directly.

If you are Mac user, use ```brew``` to install ```ngrok``` directly
```
brew cask install ngrok
```
Then start ```ngrok``` to generate the url by
```
ngrok http 8000
```
```ngrok``` would generate an URL and use this URL in *server setting*.

If you are using another OS, please check https://ngrok.com/ to find how to setup ```ngrok```.

That's it! please enjoy the Clova extension development.
