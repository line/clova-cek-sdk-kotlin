package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.audio.PlayBehavior
import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioPlayPayload
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.SimpleSpeech
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechInfoType
import com.linecorp.clova.extension.model.response.SpeechList
import com.linecorp.clova.extension.model.response.SpeechSet
import com.linecorp.clova.extension.model.response.SupportedLanguage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing the response deserialization")
class ResponseDeserializationTest {

    lateinit var mapper: ObjectMapper

    @Test
    fun testSimpleSpeechResponse() {
        val json = JsonResources("SimpleSpeechResponseSample.json").load()
        val response = mapper.readValue(json, ClovaExtensionResponse::class.java)
        assertTrue("0.1.0" == response.version)
        assertFalse(response.responseBody.shouldEndSession)
        assertTrue(response.responseBody.outputSpeech is SimpleSpeech)
        val simpleSpeech = response.responseBody.outputSpeech as SimpleSpeech
        assertTrue(SpeechInfoType.PlainText == simpleSpeech.values.type)
        assertTrue(SupportedLanguage.EN == simpleSpeech.values.lang)
    }

    @Test
    fun testSpeechListResponse() {
        val json = JsonResources("SpeechListResponseSample.json").load()
        val response = mapper.readValue(json, ClovaExtensionResponse::class.java)
        assertTrue(response.responseBody.outputSpeech is SpeechList)
        val speechList = response.responseBody.outputSpeech as SpeechList
        assertTrue(2 == speechList.values.size)
        val speechInfo1 = speechList.values[0]
        val speechInfo2 = speechList.values[1]
        assertTrue(SupportedLanguage.EN == speechInfo1.lang)
        assertTrue(SupportedLanguage.JA == speechInfo2.lang)
        assertTrue("This is test1" == speechInfo1.value)
        assertTrue("This is test2" == speechInfo2.value)
    }


    @Test
    fun testSpeechSetResponse() {
        val json = JsonResources("SpeechSetResponseSample.json").load()
        val response = mapper.readValue(json, ClovaExtensionResponse::class.java)
        assertTrue(response.responseBody.outputSpeech is SpeechSet)
        val speechSet = response.responseBody.outputSpeech as SpeechSet
        assertTrue(speechSet.values["brief"] is SpeechInfo)
        assertTrue(speechSet.values["verbose"] is SpeechList)

        val brief = speechSet.values["brief"] as SpeechInfo
        val verbose = speechSet.values["verbose"] as SpeechList
        assertTrue(SupportedLanguage.JA == brief.lang)
        assertTrue(SpeechInfoType.PlainText == brief.type)
        assertTrue(2 == verbose.values.size)
        assertTrue(SupportedLanguage.JA == verbose.values[0].lang)
        assertTrue(SpeechInfoType.PlainText == verbose.values[0].type)
    }

    @Test
    fun testResponseWithAudioPlayDirective() {
        val json = JsonResources("SimpleSpeechResponseWithDirectives.json").load()
        val response = mapper.readValue(json, ClovaExtensionResponse::class.java)
        assertTrue(response.responseBody.directives.size == 1)
        val directive = response.responseBody.directives[0]
        assertTrue(directive.header.name == DirectiveName.PLAY)
        assertTrue(directive.header.nameSpace == DirectiveNameSpace.AUDIO_PLAYER)
        assertTrue(directive.header.messageId == "05796344-9089-4f05-a951-0af0e4046e73")
        assertTrue(directive.header.dialogRequestId == "test")

        assertTrue(directive.payload is AudioPlayPayload)
        val audioPlayPayload = directive.payload as AudioPlayPayload
        assertTrue(audioPlayPayload.audioItem.stream.url == "http://test.com")
        assertTrue(audioPlayPayload.playBehavior == PlayBehavior.REPLACE_ALL)
        assertTrue(audioPlayPayload.source.name == "wahaha")
    }

    @BeforeEach
    fun setUp() {
        mapper = clovaObjectMapper()
    }
}
