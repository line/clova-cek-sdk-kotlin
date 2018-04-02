package com.linecorp.clova.extension.converter.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.converter.jackson.validateJsonProperty
import com.linecorp.clova.extension.exception.MissingJsonPropertyException
import com.linecorp.clova.extension.exception.NonsupportedRequestTypeException
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.core.Application
import com.linecorp.clova.extension.model.core.AudioPlayer
import com.linecorp.clova.extension.model.core.AudioStreamInfo
import com.linecorp.clova.extension.model.core.ContentLayer
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.core.Device
import com.linecorp.clova.extension.model.core.Display
import com.linecorp.clova.extension.model.core.DisplaySize
import com.linecorp.clova.extension.model.core.Orientation
import com.linecorp.clova.extension.model.core.PlayerActivity
import com.linecorp.clova.extension.model.core.User
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.CustomRequest
import com.linecorp.clova.extension.model.request.Intent
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.Session
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import com.linecorp.clova.extension.model.request.SlotValue
import java.time.ZonedDateTime

internal class RequestDataFactory(
        private val parser: JsonParser,
        private val isVerifyProperties: Boolean)
{
    private val attributeMapper: ObjectMapper by lazy { ObjectMapper() }

    //private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Throws(MissingJsonPropertyException::class)
    fun toCustomExtensionRequest(node: JsonNode): CustomExtensionRequest {
        validateProperty(node, JsonProperties.VERSION)
        validateProperty(node, JsonProperties.SESSION)
        validateProperty(node, JsonProperties.CONTEXT)
        validateProperty(node, JsonProperties.REQUEST)

        return CustomExtensionRequest(
                version = node[JsonProperties.VERSION].asText(),
                session = toSession(node[JsonProperties.SESSION]),
                context = toContext(node[JsonProperties.CONTEXT]),
                request = toClovaRequest(node[JsonProperties.REQUEST])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSession(node: JsonNode): Session {
        validateProperty(node, JsonProperties.NEW)
        validateProperty(node, JsonProperties.SESSION_ID)
        validateProperty(node, JsonProperties.USER)

        val sessionAttributes: Map<String, Any>? = attributeMapper.convertValue(
                node[JsonProperties.SESSION_ATTRIBUTES], object : TypeReference<Map<String, Any>>() {})

        //The test intent from dev site may not include sessionAttributes. It violates the clova document
        return Session(
                isNew = node[JsonProperties.NEW].asBoolean(),
                sessionAttributes = sessionAttributes?.let { it } ?: mapOf(),
                sessionId = node[JsonProperties.SESSION_ID].asText(),
                user = toUser(node[JsonProperties.USER])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toUser(node: JsonNode): User {
        validateProperty(node, JsonProperties.USER_ID)
        return User(
                id = node[JsonProperties.USER_ID].asText(),
                accessToken = node[JsonProperties.ACCESS_TOKEN]?.asText()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toContext(node: JsonNode): Context {
        validateProperty(node, JsonProperties.SYSTEM)
        val audioPlayer = node[JsonProperties.AUDIO_PLAYER]?.let { toAudioPlayer(it) }
        return Context(
                system = toSystem(node[JsonProperties.SYSTEM]),
                audioPlayer = audioPlayer
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toAudioPlayer(node: JsonNode): AudioPlayer {
        validateProperty(node, JsonProperties.PLAYER_ACTIVITY)
        val audioStreamInfo = node[JsonProperties.STREAM]?.let { toAudioStreamInfo(it) }
        return AudioPlayer(
                activity = PlayerActivity.of(node[JsonProperties.PLAYER_ACTIVITY].asText()),
                offsetInMilliseconds = node[JsonProperties.OFFSET_INMILLI_SECONDS]?.asInt(),
                stream = audioStreamInfo,
                totalInMilliseconds = node[JsonProperties.OFFSET_INMILLI_SECONDS]?.asInt()

        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toAudioStreamInfo(node: JsonNode): AudioStreamInfo {
        //TODO: implement it later when the document has this infomation
        return AudioStreamInfo()
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSystem(node: JsonNode): com.linecorp.clova.extension.model.core.System {
        //the test request of clova developer site doesn't include application
        validateProperty(node, JsonProperties.USER)
        validateProperty(node, JsonProperties.DEVICE)

        return com.linecorp.clova.extension.model.core.System(
                application = node[JsonProperties.APPLICATION]?.let { toApplication(it) },
                user = toUser(node[JsonProperties.USER]),
                device = toDevice(node[JsonProperties.DEVICE])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toApplication(node: JsonNode): Application {
        validateProperty(node, JsonProperties.APPLICATION_ID)
        return Application(node[JsonProperties.APPLICATION_ID].asText())
    }

    @Throws(MissingJsonPropertyException::class)
    fun toDevice(node: JsonNode): Device {
        validateProperty(node, JsonProperties.DEVICE_ID)
        validateProperty(node, JsonProperties.DISPLAY)
        return Device(
                id = node[JsonProperties.DEVICE_ID].asText(),
                display = toDisplay(node[JsonProperties.DISPLAY])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toDisplay(node: JsonNode): Display {
        validateProperty(node, JsonProperties.SIZE)

        return Display(
                size = DisplaySize.of(node[JsonProperties.SIZE].asText()),
                orientation = Orientation.of(node[JsonProperties.ORIENTATION]?.asText()),
                dpi = node[JsonProperties.DPI]?.asInt(),
                contentLayer = node[JsonProperties.CONTENT_LAYER]?.let { toContentLayer(it) }
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toContentLayer(node: JsonNode): ContentLayer {
        validateProperty(node, JsonProperties.WIDTH)
        validateProperty(node, JsonProperties.HEIGHT)
        return ContentLayer(
                width = node[JsonProperties.WIDTH].asInt(),
                height = node[JsonProperties.HEIGHT].asInt())
    }

    @Throws(MissingJsonPropertyException::class, NonsupportedRequestTypeException::class)
    fun toClovaRequest(node: JsonNode): CustomRequest {
        validateProperty(node, JsonProperties.TYPE)
        val type = node[JsonProperties.TYPE].asText()
        return when(type.toLowerCase()) {
            "launchrequest" -> toLaunchRequest(node)
            "intentrequest" -> toIntentRequest(node)
            "sessionendedrequest" -> toSesionEndedRequest(node)
            else -> throw NonsupportedRequestTypeException("Request type: $type is not supported")
        }
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSesionEndedRequest(node: JsonNode): SessionEndedRequest {
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
    fun toIntentRequest(node: JsonNode): IntentRequest {
        //we can't verify REQUEST_ID, TIMESTAMP, LOCALE because the sample response from conversation model test is incorrect.
        validateProperty(node, JsonProperties.INTENT)
        val date = node[JsonProperties.TIMESTAMP]?.asText()?.let { ZonedDateTime.parse(it)}
        return IntentRequest(
                timestamp = date?.toEpochSecond(),
                locale = node[JsonProperties.LOCALE]?.asText(),
                extensionId = node[JsonProperties.EXTENSION_ID]?.asText(),
                intent = toIntent(node[JsonProperties.INTENT])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toLaunchRequest(node: JsonNode): LaunchRequest {
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
    fun toIntent(node: JsonNode): Intent {
        validateProperty(node, JsonProperties.NAME)
        validateProperty(node, JsonProperties.SLOTS)
        return Intent(
                name = node[JsonProperties.NAME].asText(),
                slots = toSlots(node[JsonProperties.SLOTS])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSlots(node: JsonNode): Map<String, SlotValue> {
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
}
