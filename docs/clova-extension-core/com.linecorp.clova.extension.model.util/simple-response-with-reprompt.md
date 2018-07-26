[clova-extension-core](../index.md) / [com.linecorp.clova.extension.model.util](index.md) / [simpleResponseWithReprompt](./simple-response-with-reprompt.md)

# simpleResponseWithReprompt

`fun simpleResponseWithReprompt(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, repromptMessage: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, endSession: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`ClovaExtensionResponse`](../com.linecorp.clova.extension.model.response/-clova-extension-response/index.md)

The helper function to create simple voice response with the reprompt message

### Parameters

`message` - The response message

`repromptMessage` - The repromt message

`endSession` - If true, the Clova would terminate the conversation after response. Default is false

**Return**
Clova extension response.

