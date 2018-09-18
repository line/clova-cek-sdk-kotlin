/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.audio

data class AudioItem(
        val audioItemId: String,
        val titleText: String,
        val artImageUrl: String? = null,
        val headerText: String? = null,
        val stream: AudioStreamInfo,
        val titleSubText1: String,
        val titleSubText2: String? = null
)
