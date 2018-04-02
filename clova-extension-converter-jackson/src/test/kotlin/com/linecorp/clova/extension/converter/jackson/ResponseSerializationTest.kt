package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.ResponseBody
import com.linecorp.clova.extension.model.response.SimpleSpeech
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechInfoType
import com.linecorp.clova.extension.model.response.SpeechList
import com.linecorp.clova.extension.model.response.SpeechSet
import com.linecorp.clova.extension.model.response.SupportedLanguage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing the response serialization")
class ResponseSerializationTest {

    lateinit var mapper: ObjectMapper

    @Test
    fun testResponseSerialization() {
        val clovaResponse = ClovaExtensionResponse(
                version = "0.1.0",
                sessionAttributes = mapOf(),
                responseBody = ResponseBody(
                        outputSpeech = SimpleSpeech(
                                values = SpeechInfo(
                                        type = SpeechInfoType.PlainText,
                                        lang = SupportedLanguage.EN,
                                        value = "This is test"
                                )
                        ),
                        directives = arrayListOf(),
                        cards = arrayListOf(),
                        shouldEndSession = false
                )
        )
        val result = mapper.writeValueAsString(clovaResponse)
        assertTrue(result == "{\"version\":\"0.1.0\",\"sessionAttributes\":{},\"response\":{" +
                "\"outputSpeech\":{\"type\":\"SimpleSpeech\",\"values\":{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"}}," +
                "\"card\":{},\"directives\":[],\"shouldEndSession\":false}}")
    }

    @Test
    fun testSimpleSpeech() {
        val speech = SimpleSpeech(values = SpeechInfo(
                type = SpeechInfoType.PlainText,
                lang = SupportedLanguage.EN,
                value = "This is test"
        ))
        val result = mapper.writeValueAsString(speech)
        assertTrue("{\"type\":\"SimpleSpeech\",\"values\":" +
                "{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"" +
                "}}"
                == result)
    }

    @Test
    fun testSpeechList() {
        val speech = SpeechList(values = arrayListOf(SpeechInfo(
                type = SpeechInfoType.PlainText,
                lang = SupportedLanguage.EN,
                value = "This is test"
        )))
        val result = mapper.writeValueAsString(speech)
        assertTrue("{\"type\":\"SpeechList\",\"values\":" +
                "[{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"}" +
                "]}"
                == result)
    }

    @Test
    fun testSpeechSet() {
        val speech = SpeechSet(values = hashMapOf(
                "test1" to SpeechInfo(
                        type = SpeechInfoType.PlainText,
                        lang = SupportedLanguage.EN,
                        value = "This is test1"
                ),
                "test2" to SpeechInfo(
                        type = SpeechInfoType.PlainText,
                        lang = SupportedLanguage.JA,
                        value = "This is test2"
                )
        ))
        val result = mapper.writeValueAsString(speech)
        assertTrue("{\"type\":\"SpeechSet\",\"test1\":{" +
                "\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test1\"}," +
                "\"test2\":{\"type\":\"PlainText\",\"lang\":\"ja\",\"value\":\"This is test2\"}" +
                "}"
                == result)
    }

    @Test
    fun testSpeechInfoWithPlainText() {
        val speechInfo = SpeechInfo(
                type = SpeechInfoType.PlainText,
                lang = SupportedLanguage.EN,
                value = "This is test"
        )
        val result = mapper.writeValueAsString(speechInfo)
        assertTrue("{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"}" == result)
    }

    @Test
    fun testSpeechInfoWithJapanese() {
        val speechInfo = SpeechInfo(
                type = SpeechInfoType.PlainText,
                lang = SupportedLanguage.JA,
                value = "This is test"
        )

        val result = mapper.writeValueAsString(speechInfo)
        assertTrue("{\"type\":\"PlainText\",\"lang\":\"ja\",\"value\":\"This is test\"}" == result)
    }

    @Test
    fun testSpeechInfoWithPlainUrl() {
        val speechInfo = SpeechInfo(
                type = SpeechInfoType.Url,
                lang = SupportedLanguage.EN,
                value = "This is test"
        )

        val result = mapper.writeValueAsString(speechInfo)
        assertTrue("{\"type\":\"URL\",\"lang\":\"en\",\"value\":\"This is test\"}" == result)
    }

    @Test
    fun testReprompt() {
        val clovaResponse = ClovaExtensionResponse(
                version = "0.1.0",
                sessionAttributes = mapOf(),
                responseBody = ResponseBody(
                        outputSpeech = SimpleSpeech(
                                values = SpeechInfo(
                                        type = SpeechInfoType.PlainText,
                                        lang = SupportedLanguage.EN,
                                        value = "This is test"
                                )
                        ),
                        directives = arrayListOf(),
                        cards = arrayListOf(),
                        shouldEndSession = false,
                        reprompt = SimpleSpeech(
                                values = SpeechInfo(
                                        type = SpeechInfoType.PlainText,
                                        lang = SupportedLanguage.EN,
                                        value = "This is reprompt test"
                                )
                        )
                )
        )

        val result = mapper.writeValueAsString(clovaResponse)
        assertTrue(result == "{\"version\":\"0.1.0\",\"sessionAttributes\":{},\"response\":{" +
                "\"outputSpeech\":{" +
                "\"type\":\"SimpleSpeech\"," +
                "\"values\":{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"}}," +
                "\"card\":{}," +
                "\"directives\":[]," +
                "\"shouldEndSession\":false," +
                "\"reprompt\":{\"outputSpeech\":{" +
                "\"type\":\"SimpleSpeech\"," +
                "\"values\":{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is reprompt test\"}}}}}")
    }

    @Test
    fun testSessionAttributes() {
        val clovaResponse = ClovaExtensionResponse(
                version = "0.1.0",
                sessionAttributes = mapOf(
                        "myValue" to 1234,
                        "myStr" to "TEST"
                ),
                responseBody = ResponseBody(
                        outputSpeech = SimpleSpeech(
                                values = SpeechInfo(
                                        type = SpeechInfoType.PlainText,
                                        lang = SupportedLanguage.EN,
                                        value = "This is test"
                                )
                        ),
                        directives = arrayListOf(),
                        cards = arrayListOf(),
                        shouldEndSession = false
                )
        )
        val result = mapper.writeValueAsString(clovaResponse)
        assertTrue(result == "{\"version\":\"0.1.0\",\"sessionAttributes\":{\"myValue\":1234,\"myStr\":\"TEST\"},\"response\":{" +
                "\"outputSpeech\":{\"type\":\"SimpleSpeech\",\"values\":{\"type\":\"PlainText\",\"lang\":\"en\",\"value\":\"This is test\"}}," +
                "\"card\":{},\"directives\":[],\"shouldEndSession\":false}}")
    }

    @BeforeEach
    internal fun setUp() {
        mapper = clovaObjectMapper()
    }
}
