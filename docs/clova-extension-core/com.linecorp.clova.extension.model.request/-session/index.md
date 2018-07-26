[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.model.request](../index.md) / [Session](./index.md)

# Session

`data class Session`

The conversation session, each conversation has individual session id and attributes

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Session(isNew: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, sessionAttributes: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>, sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`User`](../../com.linecorp.clova.extension.model.core/-user/index.md)`)`<br>The conversation session, each conversation has individual session id and attributes |

### Properties

| Name | Summary |
|---|---|
| [isNew](is-new.md) | `val isNew: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Set true if it is new session |
| [sessionAttributes](session-attributes.md) | `val sessionAttributes: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>The user-defined data |
| [sessionId](session-id.md) | `val sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The session ID. |
| [user](user.md) | `val user: `[`User`](../../com.linecorp.clova.extension.model.core/-user/index.md)<br>The user data |
