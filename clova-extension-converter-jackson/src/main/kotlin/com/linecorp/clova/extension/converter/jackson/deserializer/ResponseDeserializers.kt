package com.linecorp.clova.extension.converter.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import java.io.IOException

internal class ResponseDeserializer :
        StdDeserializer<ClovaExtensionResponse>(ClovaExtensionResponse::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(parser: JsonParser,
                             context: DeserializationContext): ClovaExtensionResponse {

        val factory = ResponseDataFactory()
        val node: JsonNode = parser.readValueAsTree()
        return factory.toClovaExtensionResponse(node)
    }
}
