/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.util

import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.ResponseBuilder
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechInfoType
import com.linecorp.clova.extension.model.response.SpeechList
import com.linecorp.clova.extension.model.response.SupportedLanguage

/**
 * The helper function to create simple speech list response
 *
 * @param messages The list of response message
 * @param endSession If true, the Clova would terminate the conversation after response. Default is false
 * @return Clova extension response.
 */
fun speechListResponse(messages: List<String>, endSession: Boolean = false): ClovaExtensionResponse {
    val speechList = SpeechList(
            messages.map {
                SpeechInfo(type = SpeechInfoType.PlainText, lang = SupportedLanguage.JA, value = it)
            }
    )
    return ResponseBuilder().apply {
        shouldEndSession = endSession
        outputSpeech = speechList
    }.build()
}

/**
 * The helper function to create simple speech list response with reprompt message
 *
 * @param messages The list of response message
 * @param repromptMessage The reprompt message
 * @param endSession If true, the Clova would terminate the conversation after response. Default is false
 * @return Clova extension response.
 */
fun speechListResponseWithReprompt(
        messages: List<String>,
        repromptMessage: String,
        endSession: Boolean = false
): ClovaExtensionResponse {
    val speechList = SpeechList(
            messages.map {
                SpeechInfo(type = SpeechInfoType.PlainText, lang = SupportedLanguage.JA, value = it)
            }
    )
    return ResponseBuilder().apply {
        this.shouldEndSession = endSession
        this.repromptMessage = repromptMessage
        outputSpeech = speechList
    }.build()
}
