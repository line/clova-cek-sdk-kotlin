/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.payload

import com.linecorp.clova.extension.model.audio.AudioItem
import com.linecorp.clova.extension.model.audio.AudioSource
import com.linecorp.clova.extension.model.audio.AudioStreamInfo
import com.linecorp.clova.extension.model.audio.PlayBehavior

/**
 * The payload for AudioPlayer.Play directive
 * Check [official document](https://clova-developers.line.me/guide/#/CEK/References/CEK_API.md#Play) for detail
 */
data class AudioPlayPayload(
        val audioItem: AudioItem,
        val source: AudioSource,
        val playBehavior: PlayBehavior
) : Payload


data class StreamDeliverPayload(
        val audioItemId: String,
        val stream: AudioStreamInfo
) : Payload

data class PlayStatusPayload(
        val token: String,
        val offsetInMilliseconds: Long
) : Payload
