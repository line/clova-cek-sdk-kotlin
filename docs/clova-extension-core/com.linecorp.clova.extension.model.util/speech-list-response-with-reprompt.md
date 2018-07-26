[clova-extension-core](../index.md) / [com.linecorp.clova.extension.model.util](index.md) / [speechListResponseWithReprompt](./speech-list-response-with-reprompt.md)

# speechListResponseWithReprompt

`fun speechListResponseWithReprompt(messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, repromptMessage: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, endSession: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`ClovaExtensionResponse`](../com.linecorp.clova.extension.model.response/-clova-extension-response/index.md)

The helper function to create simple speech list response with reprompt message

### Parameters

`messages` - The list of response message

`repromptMessage` - The reprompt message

`endSession` - If true, the Clova would terminate the conversation after response. Default is false

**Return**
Clova extension response.

