/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.directive

import com.linecorp.clova.extension.model.core.Payload
import com.linecorp.clova.extension.model.payload.AudioPlayPayload
import com.linecorp.clova.extension.model.payload.EmptyPayload
import java.util.*

data class Directive(
        val header: DirectiveHeader,
        val payload: Payload = EmptyPayload()
) {
    /**
     * The factory class for AudioPlay directives
     *
     * @property requestId The request ID from Clova
     */
    class AudioPlayFactory(private val requestId: String) {
        fun getPlay(audioPlayPayload: AudioPlayPayload): Directive = Directive(
                header = DirectiveHeader.Factory().getPlay(requestId),
                payload = audioPlayPayload
        )
    }
}

/**
 * The header of directive
 */
data class DirectiveHeader(
        val name: String,
        val nameSpace: String,
        val dialogRequestId: String, // same as requestId
        val messageId: String
) {
    class Factory {
        private val messageId = UUID.randomUUID().toString()
        fun getPlay(requestId: String) = DirectiveHeader(
                name = DirectiveName.PLAY,
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                dialogRequestId = requestId,
                messageId = messageId
        )
    }
}
