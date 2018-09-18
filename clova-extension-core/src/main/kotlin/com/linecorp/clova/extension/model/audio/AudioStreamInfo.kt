/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.audio

data class AudioStreamInfo(
        val token: String,
        val url: String,
        val urlPlayable: Boolean,
        val beginAtInMilliseconds: Long,
        val durationInMilliseconds: Long? = null,
        val customData: String? = null,
        val progressReport: ProgressReport? = null
)
