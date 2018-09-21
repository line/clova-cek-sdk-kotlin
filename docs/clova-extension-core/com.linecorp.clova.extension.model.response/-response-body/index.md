[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.response](../index.md) / [ResponseBody](./index.md)

# ResponseBody

`data class ResponseBody`

The response body for [ClovaExtensionResponse](../-clova-extension-response/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ResponseBody(outputSpeech: `[`Speech`](../-speech.md)`, directives: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`Directive`](../../com.linecorp.clova.extension.model.directive/-directive/index.md)`>, cards: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, shouldEndSession: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, reprompt: `[`Speech`](../-speech.md)`? = null)`<br>The response body for [ClovaExtensionResponse](../-clova-extension-response/index.md) |

### Properties

| Name | Summary |
|---|---|
| [cards](cards.md) | `val cards: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>Reserved, it is not used in Japan |
| [directives](directives.md) | `val directives: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`Directive`](../../com.linecorp.clova.extension.model.directive/-directive/index.md)`>`<br>The extra data that Clova response to client, client need to handle it separately. It is not supported in this SDK yet. |
| [outputSpeech](output-speech.md) | `val outputSpeech: `[`Speech`](../-speech.md)<br>The voice response for the conversation. |
| [reprompt](reprompt.md) | `val reprompt: `[`Speech`](../-speech.md)`?`<br>If not null, Clova will prompt again when users don't response for a while |
| [shouldEndSession](should-end-session.md) | `val shouldEndSession: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Indicate that if Clova should terminate the conversation. |
