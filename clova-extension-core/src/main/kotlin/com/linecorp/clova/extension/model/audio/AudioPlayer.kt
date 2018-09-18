/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.audio

enum class PlayerActivity {
    IDLE,
    PLAYING,
    PAUSED,
    STOPPED;

    companion object {
        @JvmStatic
        fun of(value: String?): PlayerActivity {
            return when(value?.toLowerCase()) {
                "playing" -> PLAYING
                "paused" -> PAUSED
                "stopped" -> STOPPED
                else -> IDLE
            }
        }
    }
}

data class AudioPlayer(
        val activity: PlayerActivity,
        val offsetInMilliseconds: Int? = null,
        val stream: AudioStreamInfo? = null,
        val totalInMilliseconds: Int? = null
)
