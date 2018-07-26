[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.response](../index.md) / [SpeechInfo](./index.md)

# SpeechInfo

`data class SpeechInfo`

The definition of the basic voice response.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SpeechInfo(type: `[`SpeechInfoType`](../-speech-info-type/index.md)`, lang: `[`SupportedLanguage`](../-supported-language/index.md)`, value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`<br>The definition of the basic voice response. |

### Properties

| Name | Summary |
|---|---|
| [lang](lang.md) | `val lang: `[`SupportedLanguage`](../-supported-language/index.md)<br>Supported languages, If the type is [SpeechInfoType.Url](../-speech-info-type/-url.md), this value would be NONE. |
| [type](type.md) | `val type: `[`SpeechInfoType`](../-speech-info-type/index.md)<br>the type of speech info |
| [value](value.md) | `val value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the response message |
