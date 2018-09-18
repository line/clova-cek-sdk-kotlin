/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.model.audio

data class ProgressReport(
        val delayInMilliseconds: Long? = null,
        val intervalInMilliseconds: Long? = null,
        val positionInMilliseconds: Long? = null
)
