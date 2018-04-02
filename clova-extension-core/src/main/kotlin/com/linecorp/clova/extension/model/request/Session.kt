package com.linecorp.clova.extension.model.request

import com.linecorp.clova.extension.model.core.User

/**
 * The conversation session, each conversation has individual session id and attributes
 *
 * @property isNew Set true if it is new session
 * @property sessionAttributes The user-defined data
 * @property sessionId The session ID.
 * @property user The user data
 */
data class Session(
        val isNew: Boolean,
        val sessionAttributes: Map<String, Any>,
        val sessionId: String,
        val user: User
)
