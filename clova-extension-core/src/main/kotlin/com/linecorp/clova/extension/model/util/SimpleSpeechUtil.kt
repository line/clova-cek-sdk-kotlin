package com.linecorp.clova.extension.model.util

import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.ResponseBuilder
import com.linecorp.clova.extension.model.response.SimpleSpeech
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechInfoType
import com.linecorp.clova.extension.model.response.SupportedLanguage

/**
 * The helper function to create simple voice response
 *
 * @param message The response message
 * @param endSession If true, the Clova would terminate the conversation after response. Default is false
 * @return Clova extension response.
 */
fun simpleResponse(message: String, endSession: Boolean = false): ClovaExtensionResponse {
    val speech = SimpleSpeech(
            SpeechInfo(
                    type = SpeechInfoType.PlainText,
                    lang = SupportedLanguage.JA,
                    value = message)
    )
    return ResponseBuilder().apply {
        shouldEndSession = endSession
        outputSpeech = speech
    }.build()
}

/**
 * The helper function to create simple voice response with the reprompt message
 *
 * @param message The response message
 * @param repromptMessage The repromt message
 * @param endSession If true, the Clova would terminate the conversation after response. Default is false
 * @return Clova extension response.
 */
fun simpleResponseWithReprompt(
        message: String,
        repromptMessage: String,
        endSession: Boolean = false
): ClovaExtensionResponse {
    val speech = SimpleSpeech(
            SpeechInfo(
                    type = SpeechInfoType.PlainText,
                    lang = SupportedLanguage.JA,
                    value = message)
    )
    return ResponseBuilder().apply {
        this.shouldEndSession = endSession
        this.repromptMessage = repromptMessage
        outputSpeech = speech
    }.build()
}
