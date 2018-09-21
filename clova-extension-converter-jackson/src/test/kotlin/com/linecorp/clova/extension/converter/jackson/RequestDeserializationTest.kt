package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.directive.DirectiveName
import com.linecorp.clova.extension.model.directive.DirectiveNameSpace
import com.linecorp.clova.extension.model.payload.AudioStreamPayload
import com.linecorp.clova.extension.model.payload.EmptyPayload
import com.linecorp.clova.extension.model.payload.PlayStatusPayload
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.EventRequest
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.RequestType
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing the response deserialization")
class RequestDeserializationTest {

    lateinit var mapper: ObjectMapper

    @Test
    fun testLaunchRequest() {
        val json = JsonResources("LaunchRequestSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)
        assertTrue("1.0" == request.version)
        assertTrue(RequestType.Launch == request.request.type)
        assertTrue(request.request is LaunchRequest)
        assertTrue(request.session.isNew)
        assertTrue("687dcc69-0744-41da-aa4e-a1e6fc9dec0c" == request.session.sessionId)
        assertTrue(1234 == request.session.sessionAttributes["myValue"])
        assertTrue("TEST" == request.session.sessionAttributes["myStr"])

        val launchRequest = request.request as LaunchRequest
        assertTrue("ja-JP" == launchRequest.locale)
        assertTrue("clova.example" == launchRequest.extensionId)
        assertTrue(1524474709L == launchRequest.timestamp)
    }

    @Test
    fun testIntentRequest() {
        val json = JsonResources("IntentRequestSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)
        assertTrue(RequestType.Intent == request.request.type)
        assertTrue(request.request is IntentRequest)

        val intentRequest = request.request as IntentRequest
        assertTrue("OrderPizza" == intentRequest.intent.name)
        assertEquals(1, intentRequest.intent.slots.size)
        assertEquals("pizzaType", intentRequest.intent.slots["pizzaType"]?.name)
        assertEquals("ペパロニ", intentRequest.intent.slots["pizzaType"]?.value)
    }


    @Test
    fun testSessionEndRequest() {
        val json = JsonResources("SessionEndedRequestSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)
        assertTrue(RequestType.SessionEnded == request.request.type)

        val sessionRequest = request.request as SessionEndedRequest
        assertTrue("USER_INITIATED" == sessionRequest.reason)
        assertTrue("ja-JP" == sessionRequest.locale)
        assertTrue("clova.example" == sessionRequest.extensionId)
        assertTrue(1524476372L == sessionRequest.timestamp)
    }

    @Test
    fun testWithoutSessionAttributes() {
        val json = JsonResources("LaunchRequestWithoutSessionAttributesSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)
        assertTrue("1.0" == request.version)
        assertTrue(RequestType.Launch == request.request.type)
        assertTrue(request.request is LaunchRequest)
        assertTrue(request.session.isNew)
        assertTrue("687dcc69-0744-41da-aa4e-a1e6fc9dec0c" == request.session.sessionId)
        assertTrue(null == request.session.sessionAttributes["myValue"])
        assertTrue(null == request.session.sessionAttributes["myStr"])

        val launchRequest = request.request as LaunchRequest
        assertTrue("ja-JP" == launchRequest.locale)
        assertTrue("clova.example" == launchRequest.extensionId)
        assertTrue(1524474709L == launchRequest.timestamp)
    }

    @Test
    fun testEventRequest() {
        val json = JsonResources("EventRequestSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue("1.0" == request.version)
        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)
        assertFalse(request.session.isNew)
        assertTrue("a29cfead-c5ba-474d-8745-6c1a6625f0c5" == request.session.sessionId)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.CLOVA_SKILL == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.SKILL_ENABLED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is EmptyPayload)
    }

    @Test
    fun testEventRequestWithAudioPaused() {
        val json = JsonResources("EventRequestAudioPausedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PLAY_PAUSED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithAudioResumed() {
        val json = JsonResources("EventRequestAudioResumedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PLAY_RESUMED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithAudioStopped() {
        val json = JsonResources("EventRequestAudioStoppedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PLAY_STOPPED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithAudioStarted() {
        val json = JsonResources("EventRequestAudioStartedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PLAY_STARTED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithProgressIntervalPassed() {
        val json = JsonResources("EventRequestProgressReportIntervalPassedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PROGRESS_REPORT_INTERVAL_PASSED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithProgressDelayPassed() {
        val json = JsonResources("EventRequestProgressReportDelayPassedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PROGRESS_REPORT_DELAY_PASSED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithProgressPositionPassed() {
        val json = JsonResources("EventRequestProgressReportPositionPassedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.PROGRESS_REPORT_POSITION_PASSED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is PlayStatusPayload)

        val playStatePayload = eventRequest.event.payload as PlayStatusPayload
        assertEquals(83100, playStatePayload.offsetInMilliseconds)
        assertEquals("TR-NM-4435786", playStatePayload.token)
    }

    @Test
    fun testEventRequestWithStreamRequested() {
        val json = JsonResources("EventRequestStreamRequestedSample.json").load()
        val request = mapper.readValue(json, CustomExtensionRequest::class.java)

        assertTrue(RequestType.EventRequest == request.request.type)
        assertTrue(request.request is EventRequest)

        val eventRequest = request.request as EventRequest
        assertTrue("f09874hiudf-sdf-4wku-flksdjfo4hjsdf" == eventRequest.id)
        assertTrue(DirectiveNameSpace.AUDIO_PLAYER == eventRequest.event.nameSpace)
        assertTrue(DirectiveName.STREAM_REQUESTED == eventRequest.event.name)
        assertTrue(eventRequest.event.payload is AudioStreamPayload)

        val streamPayload = eventRequest.event.payload as AudioStreamPayload
        assertEquals("ac192f4c-8f12-4a58-8ace-e3127eb297a4", streamPayload.audioItemId)
        assertEquals(0, streamPayload.stream.beginAtInMilliseconds)
        assertEquals("TR-NM-4435786", streamPayload.stream.token)
        assertEquals("http://test.com", streamPayload.stream.url)
        assertFalse(streamPayload.stream.urlPlayable)

        assertEquals(60000L, streamPayload.stream.progressReport?.positionInMilliseconds)
    }

    @BeforeEach
    internal fun setUp() {
        mapper = clovaObjectMapper()
    }
}
