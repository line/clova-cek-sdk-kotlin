[clova-extension-converter-jackson](../../index.md) / [com.linecorp.clova.extension.converter.jackson](../index.md) / [JacksonObjectMapper](./index.md)

# JacksonObjectMapper

`class JacksonObjectMapper : ObjectMapper<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`

The jackson implementation of [com.linecorp.clova.extension.client.ObjectMapper](#)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `JacksonObjectMapper()`<br>The jackson implementation of [com.linecorp.clova.extension.client.ObjectMapper](#) |

### Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | `fun <V : ClovaRequest> deserialize(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`V`](deserialize.md#V)`>): `[`V`](deserialize.md#V) |
| [serialize](serialize.md) | `fun serialize(obj: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
