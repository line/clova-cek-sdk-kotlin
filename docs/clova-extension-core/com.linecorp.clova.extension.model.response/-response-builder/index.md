[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.response](../index.md) / [ResponseBuilder](./index.md)

# ResponseBuilder

`class ResponseBuilder`

The clova response builder

### Exceptions

`IllegalArgumentException` - if the outputSpeech is null

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ResponseBuilder()`<br>The clova response builder |

### Properties

| Name | Summary |
|---|---|
| [directives](directives.md) | `var directives: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`Directive`](../../com.linecorp.clova.extension.model.directive/-directive/index.md)`>` |
| [outputSpeech](output-speech.md) | `var outputSpeech: `[`Speech`](../-speech.md)`?` |
| [repromptMessage](reprompt-message.md) | `var repromptMessage: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [responseLanguage](response-language.md) | `var responseLanguage: `[`SupportedLanguage`](../-supported-language/index.md) |
| [sessionAttributes](session-attributes.md) | `var sessionAttributes: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
| [shouldEndSession](should-end-session.md) | `var shouldEndSession: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [build](build.md) | `fun build(): `[`ClovaExtensionResponse`](../-clova-extension-response/index.md) |
