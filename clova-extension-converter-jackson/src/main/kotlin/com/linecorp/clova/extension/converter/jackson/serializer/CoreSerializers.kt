package com.linecorp.clova.extension.converter.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.linecorp.clova.extension.converter.jackson.writeFields
import com.linecorp.clova.extension.model.JsonProperties
import com.linecorp.clova.extension.model.core.Application
import com.linecorp.clova.extension.model.core.ContentLayer
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.core.Device
import com.linecorp.clova.extension.model.core.Display
import com.linecorp.clova.extension.model.core.User

import java.io.IOException

internal class ApplicationSerializer : StdSerializer<Application>(Application::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Application, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeStringField(JsonProperties.APPLICATION_ID, value.id)
        }
    }
}

internal class UserSerializer : StdSerializer<User>(User::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: User, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            generator.writeStringField(JsonProperties.USER_ID, value.id)
            generator.writeStringField(JsonProperties.ACCESS_TOKEN, value.accessToken)
        }
    }
}

internal class DeviceSerializer : StdSerializer<Device>(Device::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Device, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            generator.writeStringField(JsonProperties.DEVICE_ID, value.id)
            generator.writeObjectField(JsonProperties.DISPLAY, value.display)
        }
    }
}

internal class DisplaySerializer : StdSerializer<Display>(Display::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Display, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            generator.writeStringField(JsonProperties.SIZE, value.size.value)
            value.orientation?.let {
                generator.writeStringField(JsonProperties.ORIENTATION, it.toString().toLowerCase())
            }
            value.dpi?.let {
                generator.writeNumberField(JsonProperties.DPI, it)
            }

            value.contentLayer?.let {
                generator.writeObjectField(JsonProperties.CONTENT_LAYER, it)
            }
        }
    }
}

internal class ContentLayerSerializer : StdSerializer<ContentLayer>(ContentLayer::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: ContentLayer, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            generator.writeNumberField(JsonProperties.WIDTH, value.width)
            generator.writeNumberField(JsonProperties.HEIGHT, value.height)
        }
    }
}

internal class ContextSerializer : StdSerializer<Context>(Context::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Context, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeFields {
            writeObjectField(JsonProperties.SYSTEM, value.system)
            value.audioPlayer?.let {
                writeObjectField(JsonProperties.AUDIO_PLAYER, it)
            }
        }
    }
}

internal class SystemSerializer : StdSerializer<com.linecorp.clova.extension.model.core.System>(
        com.linecorp.clova.extension.model.core.System::class.java) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
            value: com.linecorp.clova.extension.model.core.System,
            generator: JsonGenerator, provider: SerializerProvider
    ) {
        generator.writeFields {
            writeObjectField(JsonProperties.APPLICATION, value.application)
            writeObjectField(JsonProperties.USER, value.user)
            writeObjectField(JsonProperties.DEVICE, value.device)
        }
    }
}
