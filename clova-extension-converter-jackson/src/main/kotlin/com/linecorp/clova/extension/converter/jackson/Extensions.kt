package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.linecorp.clova.extension.converter.jackson.deserializer.RequestDeserializer
import com.linecorp.clova.extension.converter.jackson.deserializer.ResponseDeserializer
import com.linecorp.clova.extension.converter.jackson.serializer.ApplicationSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.ClovaResponseSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.ContentLayerSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.ContextSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.DeviceSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.DirectiveSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.DisplaySerializer
import com.linecorp.clova.extension.converter.jackson.serializer.ResponseBodySerializer
import com.linecorp.clova.extension.converter.jackson.serializer.SpeechInfoSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.SpeechSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.SystemSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.UserSerializer
import com.linecorp.clova.extension.converter.jackson.serializer.audio.ProgressReportSerializer
import com.linecorp.clova.extension.exception.MissingJsonPropertyException
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse

internal inline fun JsonGenerator.writeFields(serializer: JsonGenerator.() -> Unit) {
    writeStartObject()
    this.serializer()
    writeEndObject()
}

@Throws(MissingJsonPropertyException::class)
internal fun validateJsonProperty(node: JsonNode, name: String) {
    if (node[name] == null) {
        throw MissingJsonPropertyException("Missing mandatory property \"$name\" in node \"$node\"")
    }
}

internal fun clovaObjectMapper(isVerifyProperties: Boolean = true): ObjectMapper = ObjectMapper().apply {
    val simpleModule = SimpleModule().apply {
        addSerializer(ApplicationSerializer())
        addSerializer(UserSerializer())
        addSerializer(DeviceSerializer())
        addSerializer(DisplaySerializer())
        addSerializer(ContentLayerSerializer())
        addSerializer(ContextSerializer())
        addSerializer(SystemSerializer())

        addSerializer(ClovaResponseSerializer())
        addSerializer(ResponseBodySerializer())
        addSerializer(SpeechSerializer())
        addSerializer(SpeechInfoSerializer())
        addSerializer(ProgressReportSerializer())
        addSerializer(DirectiveSerializer())

        addDeserializer(
                CustomExtensionRequest::class.java, RequestDeserializer(isVerifyProperties))

        addDeserializer(ClovaExtensionResponse::class.java, ResponseDeserializer())
    }
    registerModule(simpleModule)
    this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
