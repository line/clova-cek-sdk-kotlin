/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.directive

class DirectiveNameSpace {
    companion object {
        const val CLOVA_SKILL = "ClovaSkill"
        const val AUDIO_PLAYER = "AudioPlayer"
        const val PLAYBACK_CONTROLLER = "PlaybackController"
    }
}

class DirectiveName {
    companion object {
        const val SKILL_ENABLED = "SkillEnabled"
        const val SKILL_DISABLED = "SkillDisabled"
        const val PLAY = "Play"
        const val PAUSE = "Pause"
        const val RESUME = "Resume"
        const val STOP = "Stop"
        const val PLAY_STARTED = "PlayStarted"
        const val PLAY_PAUSED = "PlayPaused"
        const val PLAY_RESUMED = "PlayResumed"
        const val PLAY_STOPPED = "PlayStopped"
        const val PLAY_FINISHED = "PlayFinished"
        const val PROGRESS_REPORT_DELAY_PASSED = "ProgressReportDelayPassed"
        const val PROGRESS_REPORT_INTERVAL_PASSED = "ProgressReportIntervalPassed"
        const val PROGRESS_REPORT_POSITION_PASSED = "ProgressReportPositionPassed"
        const val STREAM_DELIVER = "StreamDeliver"
        const val STREAM_REQUESTED = "StreamRequested"
        const val PLAYBACK_STATE = "PlaybackState"
    }
}

