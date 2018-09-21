/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.directive

import com.linecorp.clova.extension.model.payload.Payload
import com.linecorp.clova.extension.model.payload.AudioPlayPayload
import com.linecorp.clova.extension.model.payload.EmptyPayload
import com.linecorp.clova.extension.model.payload.AudioStreamPayload
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

        /**
         * Get the AudioPlayer.Play directive
         */
        fun getPlay(audioPlayPayload: AudioPlayPayload): Directive = Directive(
                header = DirectiveHeader.Factory().getAudioPlay(requestId),
                payload = audioPlayPayload
        )

        /**
         * Get the AudioPlayer.StreamDeliver directive
         */
        fun getStreamDeliver(streamDeliverPayload: AudioStreamPayload): Directive = Directive(
                header = DirectiveHeader.Factory().getStreamDeliver(requestId),
                payload = streamDeliverPayload
        )
    }


    /**
     * The factory class for PlaybackController directives
     *
     * @property requestId The request ID from Clova
     */
    class PlaybackController(private val requestId: String) {

        /**
         * Get the PlaybackController.Pause directive
         */
        fun getPause(): Directive = Directive(
                header = DirectiveHeader.Factory().getPlaybackControllerPause(requestId = requestId),
                payload = EmptyPayload()
        )

        /**
         * Get the PlaybackController.Resume directive
         */
        fun getResume(): Directive = Directive(
                header = DirectiveHeader.Factory().getPlaybackControllerResume(requestId = requestId),
                payload = EmptyPayload()
        )

        /**
         * Get the PlaybackController.Stop directive
         */
        fun getStop(): Directive = Directive(
                header = DirectiveHeader.Factory().getPlaybackControllerStop(requestId = requestId),
                payload = EmptyPayload()
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
        fun getAudioPlay(requestId: String) = DirectiveHeader(
                name = DirectiveName.PLAY,
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                dialogRequestId = requestId,
                messageId = messageId
        )

        fun getStreamDeliver(requestId: String) = DirectiveHeader(
                name = DirectiveName.STREAM_DELIVER,
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                dialogRequestId = requestId,
                messageId = messageId
        )

        fun getPlaybackControllerPause(requestId: String) = DirectiveHeader(
                name = DirectiveName.PAUSE,
                nameSpace = DirectiveNameSpace.PLAYBACK_CONTROLLER,
                dialogRequestId = requestId,
                messageId = messageId
        )

        fun getPlaybackControllerResume(requestId: String) = DirectiveHeader(
                name = DirectiveName.RESUME,
                nameSpace = DirectiveNameSpace.PLAYBACK_CONTROLLER,
                dialogRequestId = requestId,
                messageId = messageId
        )

        fun getPlaybackControllerStop(requestId: String) = DirectiveHeader(
                name = DirectiveName.STOP,
                nameSpace = DirectiveNameSpace.PLAYBACK_CONTROLLER,
                dialogRequestId = requestId,
                messageId = messageId
        )
    }
}
