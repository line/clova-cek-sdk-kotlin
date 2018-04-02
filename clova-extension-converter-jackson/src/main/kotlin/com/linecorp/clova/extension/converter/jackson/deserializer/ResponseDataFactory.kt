package com.linecorp.clova.extension.converter.jackson.deserializer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.converter.jackson.validateJsonProperty
import com.linecorp.clova.extension.exception.MissingJsonPropertyException
import com.linecorp.clova.extension.model.JsonProperties
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

    @Throws(MissingJsonPropertyException::class)
    fun toClovaExtensionResponse(node: JsonNode): ClovaExtensionResponse {
        return ClovaExtensionResponse(
                version = node[JsonProperties.VERSION].asText(),
                sessionAttributes = toSessionAttributes(node[JsonProperties.SESSION_ATTRIBUTES]),
                responseBody = toResponseBody(node[JsonProperties.RESPONSE])
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSessionAttributes(node: JsonNode): Map<String, Any> {
        val attributeMapper = ObjectMapper()
        return attributeMapper.convertValue(
                node, object : TypeReference<Map<String, Any>>() {})
    }

    @Throws(MissingJsonPropertyException::class)
    fun toResponseBody(node: JsonNode): ResponseBody {
        return ResponseBody(
                outputSpeech = toSpeech(node[JsonProperties.OUTPUT_SPEECH]),
                directives = arrayListOf(),
                cards = arrayListOf(),
                shouldEndSession = node[JsonProperties.SHOULD_END_SESSION].asBoolean()
        )
    }

    @Throws(MissingJsonPropertyException::class)
    fun toSpeech(node: JsonNode): Speech {
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
}
