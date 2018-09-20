package com.linecorp.clova.extension.converter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.RequestType
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import org.junit.jupiter.api.Assertions.assertEquals
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

    @BeforeEach
    internal fun setUp() {
        mapper = clovaObjectMapper()
    }
}
