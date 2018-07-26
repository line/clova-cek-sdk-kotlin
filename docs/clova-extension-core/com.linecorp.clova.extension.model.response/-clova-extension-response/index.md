[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.response](../index.md) / [ClovaExtensionResponse](./index.md)

# ClovaExtensionResponse

`data class ClovaExtensionResponse`

The Clova extension response

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ClovaExtensionResponse(version: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, sessionAttributes: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>, responseBody: `[`ResponseBody`](../-response-body/index.md)`)`<br>The Clova extension response |

### Properties

| Name | Summary |
|---|---|
| [responseBody](response-body.md) | `val responseBody: `[`ResponseBody`](../-response-body/index.md)<br>The response body, see [ResponseBody](../-response-body/index.md) |
| [sessionAttributes](session-attributes.md) | `val sessionAttributes: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>The use-defined data for current conversation session. |
| [version](version.md) | `val version: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The expected API version. It would be set If using [ResponseBuilder](../-response-builder/index.md) |
