package com.linecorp.clova.extension.model.request

import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.request.event.Event

/**
 * The basic type of clova request
 */
open class ClovaRequest

/**
 * The custom extension request
 */
data class CustomExtensionRequest(
        val version: String,
        val session: Session,
        val context: Context,
        val request: CustomRequest
) : ClovaRequest()

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

/**
 * The event request, which is used for audio application
 */
data class EventRequest(
        val id: String,
        val timestamp: Long,
        val event: Event
) : CustomRequest(RequestType.EventRequest)
