/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.audio

import java.security.InvalidParameterException

enum class PlayBehavior {
    REPLACE_ALL,
    ENQUEUE;

    companion object {
        fun of(value: String): PlayBehavior = when (value.toLowerCase()) {
            "replace_all" -> REPLACE_ALL
            "enqueue" -> ENQUEUE
            else -> throw InvalidParameterException("Only support REPLACE_ALL and ENQUEUE")
        }
    }
}
