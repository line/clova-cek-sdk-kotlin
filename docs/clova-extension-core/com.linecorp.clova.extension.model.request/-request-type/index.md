[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.request](../index.md) / [RequestType](./index.md)

# RequestType

`enum class RequestType`

The type of clova custom request. Clova supports 3 types of requests now

* Launch Request:
When users start the conversation by saying the keyword which defined in Clova Developer Center

* Intent Request:
When the conversation is activated. All interactions in the extension would be Intent Request.

* SessionEnded Request:
When users say some thing like "STOP", "TURN OFF".

Please notice that if users are idle for a while (without saying anything). Clova would terminate the extension directly.
No SessionEnded Request be sent in this case.

### Enum Values

| Name | Summary |
|---|---|
| [Launch](-launch.md) |  |
| [Intent](-intent.md) |  |
| [SessionEnded](-session-ended.md) |  |

### Properties

| Name | Summary |
|---|---|
| [value](value.md) | `val value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
