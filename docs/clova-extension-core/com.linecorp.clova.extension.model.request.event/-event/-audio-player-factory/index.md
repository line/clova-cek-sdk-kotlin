[clova-extension-core](../../../index.md) / [com.linecorp.clova.extension.model.request.event](../../index.md) / [Event](../index.md) / [AudioPlayerFactory](./index.md)

# AudioPlayerFactory

`class AudioPlayerFactory`

The factory for creating audio player events

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AudioPlayerFactory()`<br>The factory for creating audio player events |

### Functions

| Name | Summary |
|---|---|
| [getPlayFinished](get-play-finished.md) | `fun getPlayFinished(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getPlayPaused](get-play-paused.md) | `fun getPlayPaused(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getPlayResumed](get-play-resumed.md) | `fun getPlayResumed(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getPlayStopped](get-play-stopped.md) | `fun getPlayStopped(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getProgressReportDelayPassed](get-progress-report-delay-passed.md) | `fun getProgressReportDelayPassed(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getProgressReportIntervalPassed](get-progress-report-interval-passed.md) | `fun getProgressReportIntervalPassed(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getProgressReportPositionPassed](get-progress-report-position-passed.md) | `fun getProgressReportPositionPassed(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getStarted](get-started.md) | `fun getStarted(payload: `[`PlayStatusPayload`](../../../com.linecorp.clova.extension.model.payload/-play-status-payload/index.md)`): `[`Event`](../index.md) |
| [getStreamRequested](get-stream-requested.md) | `fun getStreamRequested(payload: `[`AudioStreamPayload`](../../../com.linecorp.clova.extension.model.payload/-audio-stream-payload/index.md)`): `[`Event`](../index.md) |
