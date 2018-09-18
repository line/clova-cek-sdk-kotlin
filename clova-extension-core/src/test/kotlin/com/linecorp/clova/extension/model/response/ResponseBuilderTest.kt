/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.response

import com.linecorp.clova.extension.model.audio.AudioItem
import com.linecorp.clova.extension.model.audio.AudioSource
import com.linecorp.clova.extension.model.audio.AudioStreamInfo
import com.linecorp.clova.extension.model.audio.PlayBehavior
import com.linecorp.clova.extension.model.directive.Directive
import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioPlayPayload
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResponseBuilderTest {

    @Test
    fun testResponseBuilder() {
        assertThrows<IllegalArgumentException> {
            ResponseBuilder().build()
        }
    }

    @Test
    fun testSimpleSpeech() {
        val response = ResponseBuilder().apply {
            outputSpeech = SimpleSpeech(
                    SpeechInfo(
                            type = SpeechInfoType.PlainText,
                            lang = SupportedLanguage.JA,
                            value = "This is test")
            )
        }.build()
        assertTrue(response.responseBody.outputSpeech is SimpleSpeech)
        val speech = response.responseBody.outputSpeech as SimpleSpeech
        assertTrue("This is test" == speech.values.value)
        assertTrue(SupportedLanguage.JA == speech.values.lang)
        assertTrue(SpeechInfoType.PlainText == speech.values.type)
    }

    @Test
    fun testSpeechList() {
        val response = ResponseBuilder().apply {
            outputSpeech = SpeechList(
                    listOf(SpeechInfo(
                            type = SpeechInfoType.PlainText,
                            lang = SupportedLanguage.JA,
                            value = "This is test"))
            )
        }.build()
        assertTrue(response.responseBody.outputSpeech is SpeechList)
        val speech = response.responseBody.outputSpeech as SpeechList

        assertTrue(1 == speech.values.size)
        assertTrue("This is test" == speech.values[0].value)
        assertTrue(SupportedLanguage.JA == speech.values[0].lang)
        assertTrue(SpeechInfoType.PlainText == speech.values[0].type)
    }

    @Test
    fun testReprompt() {
        val response = ResponseBuilder().apply {
            outputSpeech = SimpleSpeech(
                    SpeechInfo(
                            type = SpeechInfoType.PlainText,
                            lang = SupportedLanguage.JA,
                            value = "This is test")
            )
            repromptMessage = "repeat"
        }.build()
        assertTrue(response.responseBody.reprompt  is SimpleSpeech)
        val reprompt = response.responseBody.reprompt as SimpleSpeech
        assertTrue("repeat" == reprompt.values.value)
        assertTrue(SupportedLanguage.JA == reprompt.values.lang)
        assertTrue(SpeechInfoType.PlainText == reprompt.values.type)
    }

    @Test
    fun testDirectives() {
        val factory = Directive.AudioPlayFactory(requestId = "test")
        val audioPlayPayload = AudioPlayPayload(
                audioItem = AudioItem(
                        audioItemId = "abcde",
                        titleText = "test",
                        titleSubText1 = "subTest1",
                        stream = AudioStreamInfo(
                                token = "token",
                                url = "http://test.com",
                                urlPlayable = true,
                                beginAtInMilliseconds = 10)),
                source = AudioSource(name = "wahaha"),
                playBehavior = PlayBehavior.REPLACE_ALL
        )
        val response = ResponseBuilder().apply {
            directives = arrayListOf(factory.getPlay(audioPlayPayload))
        }

        assertTrue(response.directives.size == 1)
        val header = response.directives[0].header
        assertTrue(header.name == DirectiveName.PLAY)
        assertTrue(header.nameSpace == DirectiveNameSpace.AUDIO_PLAYER)
        assertTrue(header.dialogRequestId == "test")
    }
}
