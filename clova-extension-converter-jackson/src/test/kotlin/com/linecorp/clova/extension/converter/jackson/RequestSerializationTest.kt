package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.core.Application
import com.linecorp.clova.extension.model.core.AudioPlayer
import com.linecorp.clova.extension.model.core.AudioStreamInfo
import com.linecorp.clova.extension.model.core.ContentLayer
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.core.Device
import com.linecorp.clova.extension.model.core.Display
import com.linecorp.clova.extension.model.core.DisplaySize
import com.linecorp.clova.extension.model.core.Orientation
import com.linecorp.clova.extension.model.core.PlayerActivity
import com.linecorp.clova.extension.model.core.User
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing the data serialization")
class RequestSerializationTest {

    lateinit var mapper: ObjectMapper

    @Test
    fun testApplicationSerialization() {
        val result = mapper.writeValueAsString(Application(id = "my-test"))
        assertTrue("{\"applicationId\":\"my-test\"}" == result)
    }

    @Test
    fun testDeviceWithDisplay() {
        val device = Device(
                id = "test-id",
                display = Display(
                        size = DisplaySize.Medium,
                        orientation = Orientation.Portrait,
                        dpi = 96,
                        contentLayer = ContentLayer(427, 240))
        )
        val result = mapper.writeValueAsString(device)
        assertTrue(
                "{" +
                        "\"deviceId\":\"test-id\"," +
                        "\"display\":{" +
                        "\"size\":\"m100\"," +
                        "\"orientation\":\"portrait\"," +
                        "\"dpi\":96," +
                        "\"contentLayer\":" + "{" +
                        "\"width\":427,\"height\":240}}" +
                "}"
                == result)
    }

    @Test
    fun testDeviceWithoutDisplay() {
        val device = Device(
                id = "test-id",
                display = Display()
        )
        val result = mapper.writeValueAsString(device)
        assertTrue("{\"deviceId\":\"test-id\",\"display\":{\"size\":\"none\"}}" == result)
    }

    @Test
    fun testUser() {
        val user = User(id = "user-test", accessToken = "1234567")
        val result = mapper.writeValueAsString(user)
        assertTrue(
                "{" +
                        "\"userId\":\"user-test\"," +
                        "\"accessToken\":\"1234567\"" +
                "}"
                == result)
    }

    @Test
    fun testSystem() {
        val system = com.linecorp.clova.extension.model.core.System(
                application = null,
                user = User(id = "user-test", accessToken = "1234567"),
                device = Device(
                        id = "test-id",
                        display = Display()
                )
        )
        val result = mapper.writeValueAsString(system)

        assertTrue(
                "{" +
                        "\"application\":null," +
                        "\"user\":{\"userId\":\"user-test\",\"accessToken\":\"1234567\"}," +
                        "\"device\":{\"deviceId\":\"test-id\",\"display\":{\"size\":\"none\"}}" +
                "}" == result)
    }

    @Test
    fun testContext() {
        val context = Context(
                system = com.linecorp.clova.extension.model.core.System(
                        application = null,
                        user = User(id = "user-test", accessToken = "1234567"),
                        device = Device(
                                id = "test-id",
                                display = Display()
                        )
                )
        )

        val result = mapper.writeValueAsString(context)

        assertTrue(
                "{" +
                        "\"System\":{" +
                        "\"application\":null," +
                        "\"user\":{\"userId\":\"user-test\",\"accessToken\":\"1234567\"}," +
                        "\"device\":{\"deviceId\":\"test-id\",\"display\":{\"size\":\"none\"}}}" +
                "}" == result)
    }

    @Test
    fun testAudioPlayer() {
        val audioPlayer = AudioPlayer(
                activity = PlayerActivity.PAUSED,
                offsetInMilliseconds = 100,
                stream = AudioStreamInfo(),
                totalInMilliseconds = 1000
        )

        val result = mapper.writeValueAsString(audioPlayer)
        assertTrue(
                "{" +
                        "\"activity\":\"PAUSED\"," +
                        "\"offsetInMilliseconds\":100," +
                        "\"stream\":{}," +
                        "\"totalInMilliseconds\":1000" +
                "}" == result)
    }

    @BeforeEach
    internal fun setUp() {
        mapper = clovaObjectMapper()
    }
}
