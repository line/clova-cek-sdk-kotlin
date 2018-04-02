package com.linecorp.clova.extension.converter.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.linecorp.clova.extension.converter.jackson.writeFields
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.response.ResponseBody
import com.linecorp.clova.extension.model.response.SimpleSpeech
import com.linecorp.clova.extension.model.response.Speech
import com.linecorp.clova.extension.model.response.SpeechInfo
import com.linecorp.clova.extension.model.response.SpeechList
import com.linecorp.clova.extension.model.response.SpeechSet
import com.linecorp.clova.extension.model.response.SpeechType
import java.io.IOException

internal class ClovaResponseSerializer : StdSerializer<ClovaExtensionResponse>(ClovaExtensionResponse::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: ClovaExtensionResponse, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField(JsonProperties.VERSION, value.version)
            writeObjectField(JsonProperties.SESSION_ATTRIBUTES, value.sessionAttributes)
            writeObjectField(JsonProperties.RESPONSE, value.responseBody)
        }
    }
}

internal class ResponseBodySerializer : StdSerializer<ResponseBody>(ResponseBody::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: ResponseBody, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeObjectField(JsonProperties.OUTPUT_SPEECH, value.outputSpeech)
            writeFieldName(JsonProperties.CARD)
            writeStartObject()
            writeEndObject()

            writeArrayFieldStart(JsonProperties.DIRECTIVES)
            writeEndArray()
            writeBooleanField(JsonProperties.SHOULD_END_SESSION, value.shouldEndSession)
            value.reprompt?.let {
                //according to the document, reprompt include another "outputSpeech" object
                writeObjectFieldStart(JsonProperties.REPROMPT)
                writeObjectField(JsonProperties.OUTPUT_SPEECH, it)
                writeEndObject()
            }
        }
    }
}

internal class SpeechSerializer : StdSerializer<Speech>(Speech::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Speech, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            when(value) {
                is SimpleSpeech -> {
                    writeStringField(JsonProperties.TYPE, SpeechType.SimpleSpeech.toString())
                    writeObjectField(JsonProperties.VALUES, value.values)
                }
                is SpeechList -> {
                    writeStringField(JsonProperties.TYPE, SpeechType.SpeechList.toString())
                    writeArrayFieldStart(JsonProperties.VALUES)
                    value.values.forEach { speechInfo ->
                        writeObject(speechInfo)
                    }
                    writeEndArray()
                }
                is SpeechSet -> {
                    writeStringField(JsonProperties.TYPE, SpeechType.SpeechSet.toString())
                    value.values.forEach { speechSetElement ->
                        writeObjectField(speechSetElement.key, speechSetElement.value)
                    }
                }
            }
        }
    }
}

internal class SpeechInfoSerializer : StdSerializer<SpeechInfo>(SpeechInfo::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: SpeechInfo, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField(JsonProperties.TYPE, value.type.toString())
            writeStringField(JsonProperties.LANGUAGE, value.lang.toString())
            writeStringField(JsonProperties.VALUE, value.value)
        }
    }
}
