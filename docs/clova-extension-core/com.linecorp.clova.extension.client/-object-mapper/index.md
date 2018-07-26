[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.client](../index.md) / [ObjectMapper](./index.md)

# ObjectMapper

`interface ObjectMapper<in InType, out OutType>`

The generic interface for serialize/deserialize the object.
To implement this interface to customize the object mapper.

### Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | `abstract fun <V : `[`ClovaRequest`](../../com.linecorp.clova.extension.model.request/-clova-request/index.md)`> deserialize(value: `[`InType`](index.md#InType)`, cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`V`](deserialize.md#V)`>): `[`V`](deserialize.md#V)<br>Deserialize function for a object |
| [serialize](serialize.md) | `abstract fun serialize(obj: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`OutType`](index.md#OutType)<br>Serialize function for a object |
