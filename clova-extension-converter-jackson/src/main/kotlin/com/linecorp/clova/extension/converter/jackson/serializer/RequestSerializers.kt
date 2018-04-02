package com.linecorp.clova.extension.converter.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.linecorp.clova.extension.converter.jackson.writeFields
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.request.CustomRequest
import com.linecorp.clova.extension.model.request.Intent
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import com.linecorp.clova.extension.model.request.SlotValue
import java.io.IOException

internal class RequestSerializer : StdSerializer<CustomRequest>(CustomRequest::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: CustomRequest, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField(JsonProperties.TYPE, value.type.toString())
            writeObjectFields(value)
        }
    }

    private fun JsonGenerator.writeObjectFields(value: CustomRequest) {
        when (value) {
            is IntentRequest -> {
                writeObjectField(JsonProperties.INTENT, value.intent)
            }
            is LaunchRequest -> {
                writeObjectField(JsonProperties.REQUEST_ID, value.id)
                writeObjectField(JsonProperties.TIMESTAMP, value.timestamp)
                writeObjectField(JsonProperties.LOCALE, value.locale)
                writeObjectField(JsonProperties.EXTENSION_ID, value.extensionId)
            }
            is SessionEndedRequest -> {
                writeObjectField(JsonProperties.REQUEST_ID, value.id)
                writeObjectField(JsonProperties.TIMESTAMP, value.timestamp)
                writeObjectField(JsonProperties.LOCALE, value.locale)
                writeObjectField(JsonProperties.EXTENSION_ID, value.extensionId)
                writeObjectField(JsonProperties.REASON, value.reason)
            }
        }
    }
}

internal class IntentSerializer : StdSerializer<Intent>(Intent::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Intent, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField("name", value.name)
            writeObjectField("slots", value.slots)
        }
    }
}

internal class SlotsSerializer : StdSerializer<Map<String, SlotValue>>(Map::class.java<String, SlotValue>::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Map<String, SlotValue>, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            value.forEach {
                writeObjectField(it.key, it.value)
            }
        }
    }
}

internal class SlotValueSerializer : StdSerializer<SlotValue>(SlotValue::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: SlotValue, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField("name", value.name)
            writeStringField("value", value.value)
        }
    }
}
