/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.request.event

import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioStreamPayload
import com.linecorp.clova.extension.model.payload.EmptyPayload
import com.linecorp.clova.extension.model.payload.Payload
import com.linecorp.clova.extension.model.payload.PlayStatusPayload

data class Event(
        val nameSpace: String,
        val name: String,
        val payload: Payload
) {

    /**
     * The factory for creating audio player events
     */
    class ClovaSkillFactory {

        fun getSkillEnabled(): Event = Event(
            nameSpace = DirectiveNameSpace.CLOVA_SKILL,
            name = DirectiveName.SKILL_ENABLED,
            payload = EmptyPayload()
        )

        fun getSkillDisabled(): Event = Event(
                nameSpace = DirectiveNameSpace.CLOVA_SKILL,
                name = DirectiveName.SKILL_DISABLED,
                payload = EmptyPayload()
        )
    }

    /**
     * The factory for creating audio player events
     */
    class AudioPlayerFactory {
        fun getPlayFinished(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PLAY_FINISHED,
                payload = payload
        )

        fun getPlayPaused(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PLAY_PAUSED,
                payload = payload
        )

        fun getStarted(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PLAY_STARTED,
                payload = payload
        )

        fun getPlayStopped(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PLAY_STOPPED,
                payload = payload
        )

        fun getPlayResumed(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PLAY_RESUMED,
                payload = payload
        )

        fun getProgressReportDelayPassed(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PROGRESS_REPORT_DELAY_PASSED,
                payload = payload
        )

        fun getProgressReportIntervalPassed(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PROGRESS_REPORT_INTERVAL_PASSED,
                payload = payload
        )

        fun getProgressReportPositionPassed(payload: PlayStatusPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.PROGRESS_REPORT_POSITION_PASSED,
                payload = payload
        )

        fun getStreamRequested(payload: AudioStreamPayload): Event = Event(
                nameSpace = DirectiveNameSpace.AUDIO_PLAYER,
                name = DirectiveName.STREAM_REQUESTED,
                payload = payload
        )
    }
}
