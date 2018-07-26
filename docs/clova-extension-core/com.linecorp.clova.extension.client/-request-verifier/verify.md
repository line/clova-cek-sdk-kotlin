[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.client](../index.md) / [RequestVerifier](index.md) / [verify](./verify.md)

# verify

`abstract fun verify(signature: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, requestBody: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Verify clova request

### Parameters

`signature` - The signature of the request, it would be included in the http header

`requestBody` - The request body

**Return**
true if verification is passed.

