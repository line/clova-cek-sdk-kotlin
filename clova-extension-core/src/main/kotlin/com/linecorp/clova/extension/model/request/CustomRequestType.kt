package com.linecorp.clova.extension.model.request

/**
 * The type of clova custom request. Clova supports 3 types of requests now
 *
 * - Launch Request:
 *   When users start the conversation by saying the keyword which defined in Clova Developer Center
 *
 * - Intent Request:
 *   When the conversation is activated. All interactions in the extension would be Intent Request.
 *
 * - SessionEnded Request:
 *   When users say some thing like "STOP", "TURN OFF".
 *
 *   Please notice that if users are idle for a while (without saying anything). Clova would terminate the extension directly.
 *   No SessionEnded Request be sent in this case.
 *
 */
enum class RequestType(val value: String) {
    Launch("LaunchRequest"),
    Intent("IntentRequest"),
    SessionEnded("SessionEndedRequest");

    override fun toString(): String = this.value
}

sealed class CustomRequest(val type: RequestType)

// data class can't play well with inheritance, we have to use the same properties
data class LaunchRequest(
        val id: String,
        val timestamp: Long,
        val locale: String,
        val extensionId: String
) : CustomRequest(RequestType.Launch)

//timestamp, locale, extensionId might not exist in conversation model test
data class IntentRequest(
        val timestamp: Long?,
        val locale: String?,
        val extensionId: String?,
        val intent: Intent
) : CustomRequest(RequestType.Intent)

data class SessionEndedRequest(
        val id: String,
        val timestamp: Long,
        val locale: String,
        val extensionId: String,
        val reason: String
) : CustomRequest(RequestType.SessionEnded)
