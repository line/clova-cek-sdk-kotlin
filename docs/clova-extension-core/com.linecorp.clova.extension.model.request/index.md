[clova-extension-core](../index.md) / [com.linecorp.clova.extension.model.request](./index.md)

## Package com.linecorp.clova.extension.model.request

### Types

| Name | Summary |
|---|---|
| [ClovaRequest](-clova-request/index.md) | `open class ClovaRequest`<br>The basic type of clova request |
| [CustomExtensionRequest](-custom-extension-request/index.md) | `data class CustomExtensionRequest : `[`ClovaRequest`](-clova-request/index.md)<br>The custom extension request |
| [CustomRequest](-custom-request/index.md) | `sealed class CustomRequest` |
| [EventRequest](-event-request/index.md) | `data class EventRequest : `[`CustomRequest`](-custom-request/index.md)<br>The event request, which is used for audio application |
| [Intent](-intent/index.md) | `data class Intent` |
| [IntentRequest](-intent-request/index.md) | `data class IntentRequest : `[`CustomRequest`](-custom-request/index.md) |
| [LaunchRequest](-launch-request/index.md) | `data class LaunchRequest : `[`CustomRequest`](-custom-request/index.md) |
| [RequestType](-request-type/index.md) | `enum class RequestType`<br>The type of clova custom request. Clova supports 3 types of requests now |
| [Session](-session/index.md) | `data class Session`<br>The conversation session, each conversation has individual session id and attributes |
| [SessionEndedRequest](-session-ended-request/index.md) | `data class SessionEndedRequest : `[`CustomRequest`](-custom-request/index.md) |
| [SlotValue](-slot-value/index.md) | `data class SlotValue` |
