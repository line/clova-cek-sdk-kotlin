package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.request.ClovaRequest

/**
 * The jackson implementation of [com.linecorp.clova.extension.client.ObjectMapper]
 */
class JacksonObjectMapper: com.linecorp.clova.extension.client.ObjectMapper<String, String> {
    private val objectMapper: ObjectMapper = clovaObjectMapper()

    override fun <V : ClovaRequest> deserialize(value: String, cls: Class<V>): V =
            objectMapper.readValue(value, cls)

    override fun serialize(obj: Any): String = objectMapper.writeValueAsString(obj)
}
