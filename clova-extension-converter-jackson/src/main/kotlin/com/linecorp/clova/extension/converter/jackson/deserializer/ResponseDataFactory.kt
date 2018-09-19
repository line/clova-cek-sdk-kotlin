package com.linecorp.clova.extension.converter.jackson.deserializer

import com.fasterxml.jackson.core.JsonpCharacterEscapes
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.linecorp.clova.extension.converter.jackson.validateJsonProperty
import com.linecorp.clova.extension.exception.MissingJsonPropertyException
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.audio.AudioItem
import com.linecorp.clova.extension.model.audio.AudioSource
import com.linecorp.clova.extension.model.audio.PlayBehavior
import com.linecorp.clova.extension.model.core.Payload
import com.linecorp.clova.extension.model.directive.Directive
import com.linecorp.clova.extension.model.directive.DirectiveHeader
import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioPlayPayload
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.ResponseBody
import com.linecorp.clova.extension.model.response.SimpleSpeech
import com.linecorp.clova.extension.model.response.Speech
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechInfoType
import com.linecorp.clova.extension.model.response.SpeechList
import com.linecorp.clova.extension.model.response.SpeechSet
import com.linecorp.clova.extension.model.response.SupportedLanguage

internal class ResponseDataFactory {

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Throws(MissingJsonPropertyException::class)
    fun getClovaExtensionResponse(node: JsonNode): ClovaExtensionResponse {
        return ClovaExtensionResponse(
                version = node[JsonProperties.VERSION].asText(),
                sessionAttributes = getSessionAttributes(node[JsonProperties.SESSION_ATTRIBUTES]),
                responseBody = getResponseBody(node[JsonProperties.RESPONSE])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getSessionAttributes(node: JsonNode): Map<String, Any> {
        return objectMapper.convertValue(
                node, object : TypeReference<Map<String, Any>>() {})
    }

    @Throws(MissingJsonPropertyException::class)
    fun getResponseBody(node: JsonNode): ResponseBody {
        return ResponseBody(
                outputSpeech = getSpeech(node[JsonProperties.OUTPUT_SPEECH]),
                directives = getDirectives(node),
                cards = arrayListOf(),
                shouldEndSession = node[JsonProperties.SHOULD_END_SESSION].asBoolean()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun getDirectives(node: JsonNode): ArrayList<Directive> {

        val directives = arrayListOf<Directive>()
        node[JsonProperties.DIRECTIVES].forEach { subNode ->
            val headerNode = subNode[JsonProperties.HEADER]
            val payloadNode = subNode[JsonProperties.PAYLOAD]
            val name = headerNode[JsonProperties.NAME].asText()
            val nameSpace = headerNode[JsonProperties.NAME_SPACE].asText()
            directives.add(Directive(
                    header = DirectiveHeader(
                            name = name,
                            nameSpace = nameSpace,
                            messageId = headerNode[JsonProperties.MESSAGE_ID].asText(),
                            dialogRequestId = headerNode[JsonProperties.DIALOG_REQUEST_ID].asText()
                    ),
                    payload = createPayload(
                            name = name,
                            nameSpace = nameSpace,
                            node = payloadNode
                    )
            ))
        }
        return directives
    }


    @Throws(MissingJsonPropertyException::class)
    fun getSpeech(node: JsonNode): Speech {
        validateJsonProperty(node, JsonProperties.TYPE)
        val speechType = node[JsonProperties.TYPE].asText()
        return when (speechType.toLowerCase()) {
            "simplespeech" -> {
                val nodeValue = node[JsonProperties.VALUES]
                SimpleSpeech(values = SpeechInfo(
                        type = SpeechInfoType.of(nodeValue[JsonProperties.TYPE].asText()),
                        lang = SupportedLanguage.of(nodeValue[JsonProperties.LANGUAGE].asText()),
                        value = nodeValue[JsonProperties.VALUE].asText()
                ))
            }

            "speechlist" -> {
                createSpeechList(node)
            }

            "speechset" -> {
                val speechSet = hashMapOf<String, Any>()
                node.fields().forEach { element ->
                    if (element.key != JsonProperties.TYPE) {
                        val subType = SpeechInfoType.of(element.value[JsonProperties.TYPE].asText())
                        when(subType) {
                            SpeechInfoType.PlainText, SpeechInfoType.Url -> {
                                speechSet[element.key] = SpeechInfo(
                                        type = SpeechInfoType.of(element.value[JsonProperties.TYPE].asText()),
                                        lang = SupportedLanguage.of(element.value[JsonProperties.LANGUAGE].asText()),
                                        value = element.value[JsonProperties.VALUE].asText())
                            }
                            SpeechInfoType.SpeechList -> {
                                speechSet[element.key] = createSpeechList(element.value)
                            }
                        }

                    }
                }
                SpeechSet(values = speechSet)

            }
            else -> throw IllegalArgumentException("couldn't support speech type: $speechType")
        }
    }

    private fun createSpeechList(node: JsonNode): SpeechList {
        val speechList = arrayListOf<SpeechInfo>()
        node[JsonProperties.VALUES].forEach {
            speechList.add(SpeechInfo(
                    type = SpeechInfoType.of(it[JsonProperties.TYPE].asText()),
                    lang = SupportedLanguage.of(it[JsonProperties.LANGUAGE].asText()),
                    value = it[JsonProperties.VALUE].asText()
            ))
        }
        return SpeechList(values = speechList)
    }

    private fun createPayload(name: String, nameSpace: String, node: JsonNode): Payload =
            when (nameSpace) {
                DirectiveNameSpace.AUDIO_PLAYER -> createAudioPlaybackPayload(name, node)
                else -> TODO("not implemented yet")
            }

    private fun createAudioPlaybackPayload(name: String, node: JsonNode): Payload =
            when (name) {
                DirectiveName.PLAY -> objectMapper.convertValue(node, AudioPlayPayload::class.java)
                else -> TODO("not implemented yet")
            }
}
