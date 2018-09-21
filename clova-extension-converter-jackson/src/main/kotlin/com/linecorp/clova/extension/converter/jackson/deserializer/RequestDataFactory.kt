package com.linecorp.clova.extension.converter.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.linecorp.clova.extension.converter.jackson.validateJsonProperty
import com.linecorp.clova.extension.exception.MissingJsonPropertyException
import com.linecorp.clova.extension.exception.NonsupportedRequestTypeException
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.audio.AudioPlayer
import com.linecorp.clova.extension.model.audio.AudioStreamInfo
import com.linecorp.clova.extension.model.audio.PlayerActivity
import com.linecorp.clova.extension.model.audio.ProgressReport
import com.linecorp.clova.extension.model.core.Application
import com.linecorp.clova.extension.model.core.ContentLayer
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.core.Device
import com.linecorp.clova.extension.model.core.Display
import com.linecorp.clova.extension.model.core.DisplaySize
import com.linecorp.clova.extension.model.core.Orientation
import com.linecorp.clova.extension.model.core.User
import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioStreamPayload
import com.linecorp.clova.extension.model.payload.PlayStatusPayload
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.CustomRequest
import com.linecorp.clova.extension.model.request.EventRequest
import com.linecorp.clova.extension.model.request.Intent
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.Session
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import com.linecorp.clova.extension.model.request.SlotValue
import com.linecorp.clova.extension.model.request.event.Event
import java.lang.IllegalArgumentException
import java.time.ZonedDateTime

internal class RequestDataFactory(
        private val parser: JsonParser,
        private val isVerifyProperties: Boolean)
{
    private val mapper: ObjectMapper by lazy { ObjectMapper().registerModule(KotlinModule()) }

    //private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Throws(MissingJsonPropertyException::class)
    fun getCustomExtensionRequest(node: JsonNode): CustomExtensionRequest {
        validateProperty(node, JsonProperties.VERSION)
        validateProperty(node, JsonProperties.SESSION)
        validateProperty(node, JsonProperties.CONTEXT)
        validateProperty(node, JsonProperties.REQUEST)

        return CustomExtensionRequest(
                version = node[JsonProperties.VERSION].asText(),
                session = getSession(node[JsonProperties.SESSION]),
                context = getContext(node[JsonProperties.CONTEXT]),
                request = getClovaRequest(node[JsonProperties.REQUEST])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getSession(node: JsonNode): Session {
        validateProperty(node, JsonProperties.NEW)
        validateProperty(node, JsonProperties.SESSION_ID)
        validateProperty(node, JsonProperties.USER)

        val sessionAttributes: Map<String, Any>? = mapper.convertValue(
                node[JsonProperties.SESSION_ATTRIBUTES], object : TypeReference<Map<String, Any>>() {})

        //The test intent from dev site may not include sessionAttributes. It violates the clova document
        return Session(
                isNew = node[JsonProperties.NEW].asBoolean(),
                sessionAttributes = sessionAttributes?.let { it } ?: mapOf(),
                sessionId = node[JsonProperties.SESSION_ID].asText(),
                user = getUser(node[JsonProperties.USER])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getUser(node: JsonNode): User {
        validateProperty(node, JsonProperties.USER_ID)
        return User(
                id = node[JsonProperties.USER_ID].asText(),
                accessToken = node[JsonProperties.ACCESS_TOKEN]?.asText()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getContext(node: JsonNode): Context {
        validateProperty(node, JsonProperties.SYSTEM)
        val audioPlayer = node[JsonProperties.AUDIO_PLAYER]?.let { getAudioPlayer(it) }
        return Context(
                system = getSystem(node[JsonProperties.SYSTEM]),
                audioPlayer = audioPlayer
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getAudioPlayer(node: JsonNode): AudioPlayer {
        validateProperty(node, JsonProperties.PLAYER_ACTIVITY)
        val audioStreamInfo = node[JsonProperties.STREAM]?.let { getAudioStreamInfo(it) }
        return AudioPlayer(
                activity = PlayerActivity.of(node[JsonProperties.PLAYER_ACTIVITY].asText()),
                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS]?.asInt(),
                stream = audioStreamInfo,
                totalInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS]?.asInt()

        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getSystem(node: JsonNode): com.linecorp.clova.extension.model.core.System {
        //the test request of clova developer site doesn't include application
        validateProperty(node, JsonProperties.USER)
        validateProperty(node, JsonProperties.DEVICE)

        return com.linecorp.clova.extension.model.core.System(
                application = node[JsonProperties.APPLICATION]?.let { getApplication(it) },
                user = getUser(node[JsonProperties.USER]),
                device = getDevice(node[JsonProperties.DEVICE])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getApplication(node: JsonNode): Application {
        validateProperty(node, JsonProperties.APPLICATION_ID)
        return Application(node[JsonProperties.APPLICATION_ID].asText())
    }

    @Throws(MissingJsonPropertyException::class)
    fun getDevice(node: JsonNode): Device {
        validateProperty(node, JsonProperties.DEVICE_ID)
        validateProperty(node, JsonProperties.DISPLAY)
        return Device(
                id = node[JsonProperties.DEVICE_ID].asText(),
                display = getDisplay(node[JsonProperties.DISPLAY])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getDisplay(node: JsonNode): Display {
        validateProperty(node, JsonProperties.SIZE)

        return Display(
                size = DisplaySize.of(node[JsonProperties.SIZE].asText()),
                orientation = Orientation.of(node[JsonProperties.ORIENTATION]?.asText()),
                dpi = node[JsonProperties.DPI]?.asInt(),
                contentLayer = node[JsonProperties.CONTENT_LAYER]?.let { getContentLayer(it) }
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getContentLayer(node: JsonNode): ContentLayer {
        validateProperty(node, JsonProperties.WIDTH)
        validateProperty(node, JsonProperties.HEIGHT)
        return ContentLayer(
                width = node[JsonProperties.WIDTH].asInt(),
                height = node[JsonProperties.HEIGHT].asInt())
    }

    @Throws(MissingJsonPropertyException::class, NonsupportedRequestTypeException::class)
    fun getClovaRequest(node: JsonNode): CustomRequest {
        validateProperty(node, JsonProperties.TYPE)
        val type = node[JsonProperties.TYPE].asText()
        return when(type.toLowerCase()) {
            "launchrequest" -> getLaunchRequest(node)
            "intentrequest" -> getIntentRequest(node)
            "sessionendedrequest" -> getSessionEndedRequest(node)
            "eventrequest" -> getEventRequest(node)
            else -> throw NonsupportedRequestTypeException("Request type: $type is not supported")
        }
    }

    @Throws(MissingJsonPropertyException::class)
    fun getEventRequest(node: JsonNode): EventRequest {
        validateProperty(node, JsonProperties.REQUEST_ID)
        validateProperty(node, JsonProperties.TIMESTAMP)
        validateProperty(node, JsonProperties.EVENT)
        val eventNode = node[JsonProperties.EVENT]
        validateProperty(eventNode, JsonProperties.NAMESPACE)
        validateProperty(eventNode, JsonProperties.NAME)
        validateProperty(eventNode, JsonProperties.PAYLOAD)
        val date = ZonedDateTime.parse(node[JsonProperties.TIMESTAMP].asText())

        val name = eventNode[JsonProperties.NAME].asText()
        val namespace = eventNode[JsonProperties.NAMESPACE].asText()
        return EventRequest(
                id = node[JsonProperties.REQUEST_ID].asText(),
                timestamp = date.toEpochSecond(),
                event = getEvent(nameSpace = namespace, name = name, payloadNode = eventNode[JsonProperties.PAYLOAD])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getSessionEndedRequest(node: JsonNode): SessionEndedRequest {
        validateProperty(node, JsonProperties.REQUEST_ID)
        validateProperty(node, JsonProperties.TIMESTAMP)
        validateProperty(node, JsonProperties.LOCALE)
        validateProperty(node, JsonProperties.REASON)
        val date = ZonedDateTime.parse(node[JsonProperties.TIMESTAMP].asText())
        return SessionEndedRequest(
                id = node[JsonProperties.REQUEST_ID].asText(),
                timestamp = date.toEpochSecond(),
                locale = node[JsonProperties.LOCALE].asText(),
                extensionId = node[JsonProperties.EXTENSION_ID].asText(),
                reason = node[JsonProperties.REASON].asText()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getIntentRequest(node: JsonNode): IntentRequest {
        //we can't verify REQUEST_ID, TIMESTAMP, LOCALE because the sample response from conversation model test is incorrect.
        validateProperty(node, JsonProperties.INTENT)
        val date = node[JsonProperties.TIMESTAMP]?.asText()?.let { ZonedDateTime.parse(it)}
        return IntentRequest(
                timestamp = date?.toEpochSecond(),
                locale = node[JsonProperties.LOCALE]?.asText(),
                extensionId = node[JsonProperties.EXTENSION_ID]?.asText(),
                intent = getIntent(node[JsonProperties.INTENT])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getLaunchRequest(node: JsonNode): LaunchRequest {
        validateProperty(node, JsonProperties.REQUEST_ID)
        validateProperty(node, JsonProperties.TIMESTAMP)
        validateProperty(node, JsonProperties.LOCALE)
        val date = ZonedDateTime.parse(node[JsonProperties.TIMESTAMP].asText())

        return LaunchRequest(
                id = node[JsonProperties.REQUEST_ID].asText(),
                timestamp = date.toEpochSecond(),
                locale = node[JsonProperties.LOCALE].asText(),
                extensionId = node[JsonProperties.EXTENSION_ID].asText()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getIntent(node: JsonNode): Intent {
        validateProperty(node, JsonProperties.NAME)
        validateProperty(node, JsonProperties.SLOTS)
        return Intent(
                name = node[JsonProperties.NAME].asText(),
                slots = getSlots(node[JsonProperties.SLOTS])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getSlots(node: JsonNode): Map<String, SlotValue> {
        val map = hashMapOf<String, SlotValue>()
        node.fields().forEach { element ->
            validateProperty(element.value, JsonProperties.NAME)
            validateProperty(element.value, JsonProperties.VALUE)
            map[element.key] = SlotValue(
                    name = element.value[JsonProperties.NAME].asText(),
                    value = element.value[JsonProperties.VALUE].asText()
            )
        }
        return map
    }

    @Throws(MissingJsonPropertyException::class)
    private fun validateProperty(node: JsonNode, name: String) {
        if (isVerifyProperties) {
            validateJsonProperty(node, name)
        }
    }

    private fun getEvent(nameSpace: String, name: String, payloadNode: JsonNode): Event =
            when (nameSpace) {
                DirectiveNameSpace.CLOVA_SKILL -> getClovaSkillEvent(name)
                DirectiveNameSpace.AUDIO_PLAYER -> getAudioPlayerEvent(name, payloadNode)
                else -> throw IllegalArgumentException("Namespace $nameSpace is not supported")
            }

    private fun getClovaSkillEvent(name: String): Event =
            when (name) {
                DirectiveName.SKILL_ENABLED -> Event.ClovaSkillFactory().getSkillEnabled()
                DirectiveName.SKILL_DISABLED -> Event.ClovaSkillFactory().getSkillEnabled()
                else -> throw IllegalArgumentException("Name $name is not supported")
            }

    private fun getAudioPlayerEvent(name: String, node: JsonNode): Event =
            when (name) {
                DirectiveName.PLAY_STARTED -> Event.AudioPlayerFactory().getStarted(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PLAY_PAUSED -> Event.AudioPlayerFactory().getPlayPaused(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PLAY_STOPPED -> Event.AudioPlayerFactory().getPlayStopped(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PLAY_RESUMED -> Event.AudioPlayerFactory().getPlayResumed(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PLAY_FINISHED -> Event.AudioPlayerFactory().getPlayFinished(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PROGRESS_REPORT_DELAY_PASSED -> Event.AudioPlayerFactory().getProgressReportDelayPassed(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PROGRESS_REPORT_INTERVAL_PASSED -> Event.AudioPlayerFactory().getProgressReportIntervalPassed(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.PROGRESS_REPORT_POSITION_PASSED -> Event.AudioPlayerFactory().getProgressReportPositionPassed(
                        payload = PlayStatusPayload(
                                token = node[JsonProperties.TOKEN].asText(),
                                offsetInMilliseconds = node[JsonProperties.OFFSET_IN_MILLISECONDS].asLong()
                        )
                )
                DirectiveName.STREAM_REQUESTED -> {
                    validateProperty(node, JsonProperties.AUDIO_STREAM)
                    val streamNode = node[JsonProperties.AUDIO_STREAM]
                    val audioStream = getAudioStreamInfo(streamNode)
                    Event.AudioPlayerFactory().getStreamRequested(
                            payload = AudioStreamPayload(
                                    audioItemId = node[JsonProperties.AUDIO_ITEM_ID].asText(),
                                    stream = audioStream
                            )
                    )
                }

                else -> throw IllegalArgumentException("Name $name is not supported")
            }

    @Throws(MissingJsonPropertyException::class)
    private fun getAudioStreamInfo(node: JsonNode): AudioStreamInfo {
        validateProperty(node, JsonProperties.TOKEN)
        validateProperty(node, JsonProperties.URL)
        validateProperty(node, JsonProperties.URL_PLAYABLE)
        validateProperty(node, JsonProperties.BEGINAT_IN_MILLISECONDS)
        return AudioStreamInfo(
                token = node[JsonProperties.TOKEN].asText(),
                url = node[JsonProperties.URL].asText(),
                urlPlayable = node[JsonProperties.URL_PLAYABLE].asBoolean(),
                beginAtInMilliseconds = node[JsonProperties.BEGINAT_IN_MILLISECONDS].asLong(),
                durationInMilliseconds = node[JsonProperties.DURATION_IN_MILLISECONDS]?.asLong(),
                customData = node[JsonProperties.CUSTOM_DATA]?.asText(),
                progressReport = node[JsonProperties.PROGRESS_REPORT]?.let { getProgressReport(it) }
        )
    }

    private fun getProgressReport(node: JsonNode): ProgressReport = ProgressReport(
            delayInMilliseconds = node[JsonProperties.PROGRESS_REPORT_DELAY_IN_MILLISECONDS]?.asLong(),
            intervalInMilliseconds = node[JsonProperties.PROGRESS_REPORT_INTERVAL_IN_MILLISECONDS]?.asLong(),
            positionInMilliseconds = node[JsonProperties.PROGRESS_REPORT_POSITION_IN_MILLISECONDS]?.asLong()
    )
}
