/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.converter.jackson.serializer.audio

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.linecorp.clova.extension.converter.jackson.writeFields
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.audio.ProgressReport
import java.io.IOException

class ProgressReportSerializer : StdSerializer<ProgressReport>(ProgressReport::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: ProgressReport, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            value.delayInMilliseconds?.let {
                writeNumberField(JsonProperties.PROGRESS_REPORT_DELAY_IN_MILLISECONDS, it)
            }

            value.intervalInMilliseconds?.let {
                writeNumberField(JsonProperties.PROGRESS_REPORT_INTERVAL_IN_MILLISECONDS, it)
            }

            value.positionInMilliseconds?.let {
                writeNumberField(JsonProperties.PROGRESS_REPORT_POSITION_IN_MILLISECONDS, it)
            }
        }
    }
}
