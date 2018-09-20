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
    SessionEnded("SessionEndedRequest"),
    EventRequest("EventRequest");

    override fun toString(): String = this.value
}
