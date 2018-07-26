[clova-extension-core](../index.md) / [com.linecorp.clova.extension.client](index.md) / [clovaClient](./clova-client.md)

# clovaClient

`@Dsl fun clovaClient(applicationId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, verifier: `[`RequestVerifier`](-request-verifier/index.md)` = RequestVerifierImpl(), init: `[`ClovaClient`](-clova-client/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`ClovaClient`](-clova-client/index.md)

The definition of DSL keyword clovaClient.

The syntax is as follows:

```
val clovaClient = clovaClient {
    objectMapper = JacksonObjectMapper()
    launchHandler { intentRequest, session ->
        simpleResponse("Hello, Clova!")
    }
    intentHandler { intentRequest, session ->
        simpleResponse("What can I do?")
    }
}
```

