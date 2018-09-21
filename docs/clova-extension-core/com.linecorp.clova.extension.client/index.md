[clova-extension-core](../index.md) / [com.linecorp.clova.extension.client](./index.md)

## Package com.linecorp.clova.extension.client

### Types

| Name | Summary |
|---|---|
| [ClovaClient](-clova-client/index.md) | `class ClovaClient`<br>The ClovaClient provides the functionality for handling the requests from Clova platform, SDK also provides a DSL [(example)](clova-client.md) to create [ClovaClient](-clova-client/index.md) |
| [ObjectMapper](-object-mapper/index.md) | `interface ObjectMapper<in InType, out OutType>`<br>The generic interface for serialize/deserialize the object. To implement this interface to customize the object mapper. |
| [RequestVerifier](-request-verifier/index.md) | `interface RequestVerifier`<br>The clova request verifier. |

### Type Aliases

| Name | Summary |
|---|---|
| [RequestHandler](-request-handler.md) | `typealias RequestHandler<T> = suspend (`[`T`](-request-handler.md#T)`, `[`Session`](../com.linecorp.clova.extension.model.request/-session/index.md)`) -> `[`ClovaExtensionResponse`](../com.linecorp.clova.extension.model.response/-clova-extension-response/index.md)<br>The alias of request handler. It is suspend function with two parameters (T, Session) |

### Functions

| Name | Summary |
|---|---|
| [clovaClient](clova-client.md) | `fun clovaClient(applicationId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, verifier: `[`RequestVerifier`](-request-verifier/index.md)` = RequestVerifierImpl(), init: `[`ClovaClient`](-clova-client/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`ClovaClient`](-clova-client/index.md)<br>The definition of DSL keyword clovaClient. |
| [eventHandler](event-handler.md) | `fun `[`ClovaClient`](-clova-client/index.md)`.eventHandler(handler: `[`RequestHandler`](-request-handler.md)`<`[`EventRequest`](../com.linecorp.clova.extension.model.request/-event-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword sessionEndedHandler |
| [intentHandler](intent-handler.md) | `fun `[`ClovaClient`](-clova-client/index.md)`.intentHandler(handler: `[`RequestHandler`](-request-handler.md)`<`[`IntentRequest`](../com.linecorp.clova.extension.model.request/-intent-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword intentHandler |
| [launchHandler](launch-handler.md) | `fun `[`ClovaClient`](-clova-client/index.md)`.launchHandler(handler: `[`RequestHandler`](-request-handler.md)`<`[`LaunchRequest`](../com.linecorp.clova.extension.model.request/-launch-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword launchHandler |
| [sessionEndedHandler](session-ended-handler.md) | `fun `[`ClovaClient`](-clova-client/index.md)`.sessionEndedHandler(handler: `[`RequestHandler`](-request-handler.md)`<`[`SessionEndedRequest`](../com.linecorp.clova.extension.model.request/-session-ended-request/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>The definition of DSL keyword sessionEndedHandler |
