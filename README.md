# clova-cek-sdk-kotlin
This is clova extension sdk for kotlin. You can use this sdk with any server-side frameworks like Spring, Armeria, etc.

This SDK contains several libraries.
- *core (mandatory)*: The core functions of Clova extension
- *data converter (optional)*: The json converter for Clova, it supports those json libraries
  - Jackson  
- *echo*: The sample of clova extension based on Spring WebFlux.
- *echo-mvc*: The sample of clova extension based Spring MVC.

Because this SDK supports coroutines, please use Kotlin 1.1+.

## Setup
Add the configurations in the ```build.gradle``` as follows.
```
repositories {
    jcenter()
}

dependencies {
    //Mandatory
    implementation "com.linecorp:clova-extension-core:1.0.0"

    //Optional
    implementation "com.linecorp:clova-extension-converter-jackson:1.0.0" //the data convertor for jackson
}
```

## Getting started
To get started with the sdk, define the ```ClovaClient``` as follows
```
val client: ClovaClient = clovaClient("application.id") {
    objectMapper = JacksonObjectMapper() //it is must, use pre-defined object mapper (based on Jackson) or to create custom object mapper
      
    launchHandler { launchRequest, session ->
        //Add your implementation
    }

    intentHandler { intentRequest, session ->
        //Add your implementation
    }

    sessionEndedHandler { sessionEndedRequest, session ->
        //Add your implementation
    }
}
```

### Handle the request
You just need to call ```handleCustomExtensionRequest``` in your route handler

```
val response = client.handleClovaRequest(requestBody, httpHeaders)
```

*requestBody* and *response* is the json string.

### Coroutine support
You can use suspend function in the handler directly as follows.

```
launchHandler { launchRequest, session ->
    delay(1000)
    //implement here
}
```

Or if you want to handle the clova request inside coroutine scope, you can use ```handleClovaRequestSuspend```

```
asyc {
    client.handleClovaRequestSuspend(requestBody, httpHeaders)
}
```

To see the tutorial, please check [tutorial](TUTORIAL.md)

## References
- official document: https://clova-developers.line.me/guide/
- API documents:
    - [clova-extension-core](docs/clova-extension-core/index.md)
    - [clova-extension-converter-jackson](docs/clova-extension-converter-jackson/index.md)