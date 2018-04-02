package com.linecorp.clova.extension.model.response

/**
 * The Clova extension response
 *
 * @property version The expected API version. It would be set If using [ResponseBuilder]
 * @property sessionAttributes The use-defined data for current conversation session.
 * @property responseBody The response body, see [ResponseBody]
 */
data class ClovaExtensionResponse (
        val version: String,
        val sessionAttributes: Map<String, Any>,
        val responseBody: ResponseBody
)

/**
 * The response body for [ClovaExtensionResponse]
 *
 * @property outputSpeech The voice response for the conversation.
 * @property directives The extra data that Clova response to client, client need to handle it separately. It is not supported in this SDK yet.
 * @property cards Reserved, it is not used in Japan
 * @property shouldEndSession Indicate that if Clova should terminate the conversation.
 * @property reprompt If not null, Clova will prompt again when users don't response for a while
 */
data class ResponseBody (
        val outputSpeech: Speech,
        //TODO implement it later
        val directives: ArrayList<String>,
        val cards: ArrayList<String>,
        val shouldEndSession: Boolean,
        val reprompt: Speech? = null
)
