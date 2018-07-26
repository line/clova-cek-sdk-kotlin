[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.client](../index.md) / [ClovaClient](index.md) / [handleClovaRequestSuspend](./handle-clova-request-suspend.md)

# handleClovaRequestSuspend

`suspend fun handleClovaRequestSuspend(requestBody: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, headers: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Handle the custom extension request with suspend support

### Parameters

`requestBody` - the requestBody from clova. It supposed to be json string.

`headers` - the HTTP headers. Since the field name is case-insensitive, it would be treated as lowercase in this function.

**Return**
the response string in json format

