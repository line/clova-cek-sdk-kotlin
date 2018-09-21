[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.client](../index.md) / [ClovaClient](./index.md)

# ClovaClient

`class ClovaClient`

The ClovaClient provides the functionality for handling the requests from Clova platform,
SDK also provides a DSL [(example)](../clova-client.md) to create [ClovaClient](./index.md)

### Parameters

`applicationId` - The application id, It must be the same as the one registered in Clova Developer Center.

`verifier` - The request verifier, default is using the built-in request verifier

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ClovaClient(applicationId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, verifier: `[`RequestVerifier`](../-request-verifier/index.md)` = RequestVerifierImpl())`<br>The ClovaClient provides the functionality for handling the requests from Clova platform, SDK also provides a DSL [(example)](../clova-client.md) to create [ClovaClient](./index.md) |

### Properties

| Name | Summary |
|---|---|
| [clientVersion](client-version.md) | `val clientVersion: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The SDK version that client supports |
| [objectMapper](object-mapper.md) | `var objectMapper: `[`ObjectMapper`](../-object-mapper/index.md)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>The object mapper to convert json to ClovaRequest object |

### Functions

| Name | Summary |
|---|---|
| [handleClovaRequest](handle-clova-request.md) | `fun handleClovaRequest(requestBody: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, headers: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Handle the custom extension request synchronizedly |
| [handleClovaRequestSuspend](handle-clova-request-suspend.md) | `suspend fun handleClovaRequestSuspend(requestBody: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, headers: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Handle the custom extension request with suspend support |

### Extension Functions

| Name | Summary |
|---|---|
| [eventHandler](../event-handler.md) | `fun `[`ClovaClient`](./index.md)`.eventHandler(handler: `[`RequestHandler`](../-request-handler.md)`<`[`EventRequest`](../../com.linecorp.clova.extension.model.request/-event-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword sessionEndedHandler |
| [intentHandler](../intent-handler.md) | `fun `[`ClovaClient`](./index.md)`.intentHandler(handler: `[`RequestHandler`](../-request-handler.md)`<`[`IntentRequest`](../../com.linecorp.clova.extension.model.request/-intent-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword intentHandler |
| [launchHandler](../launch-handler.md) | `fun `[`ClovaClient`](./index.md)`.launchHandler(handler: `[`RequestHandler`](../-request-handler.md)`<`[`LaunchRequest`](../../com.linecorp.clova.extension.model.request/-launch-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword launchHandler |
| [sessionEndedHandler](../session-ended-handler.md) | `fun `[`ClovaClient`](./index.md)`.sessionEndedHandler(handler: `[`RequestHandler`](../-request-handler.md)`<`[`SessionEndedRequest`](../../com.linecorp.clova.extension.model.request/-session-ended-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword sessionEndedHandler |
