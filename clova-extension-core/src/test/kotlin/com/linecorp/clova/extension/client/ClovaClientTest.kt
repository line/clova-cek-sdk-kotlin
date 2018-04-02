/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.client

import com.linecorp.clova.extension.mock.MockVerifier
import com.linecorp.clova.extension.mock.createMockCustomExtensionRequest
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.RequestType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock

@DisplayName("Testing clova clovaClient")
class ClovaClientTest {

    private lateinit var mockMapper: ObjectMapper<String, String>
    private lateinit var client: ClovaClient

    @Test
    fun testNotAssignObjectMapper() {
        client = ClovaClient("test", MockVerifier())
        assertThrows<NotImplementedError> { client.handleClovaRequest("{}", mapOf()) }
    }

    @Test
    fun testLaunchHandlerDispatch() {
        `when`(mockMapper.deserialize("{}", CustomExtensionRequest::class.java)).thenReturn(
                createMockCustomExtensionRequest(RequestType.Launch))
        
        client = clovaClient("test", MockVerifier()) {
            objectMapper = mockMapper
            launchHandler { launchRequest, session ->

                assertTrue("session-id-3064" == session.sessionId)
                throw RuntimeException("The launchHandler was invoked")
            }
        }

        assertThrows<RuntimeException> {
            val response = client.handleClovaRequest("{}", mapOf())
        }
    }

    @Test
    internal fun testIntentRequestDispatch() {
        `when`(mockMapper.deserialize("{}", CustomExtensionRequest::class.java)).thenReturn(
                createMockCustomExtensionRequest(RequestType.Intent))

        client = clovaClient("test", MockVerifier()) {
            objectMapper = mockMapper
            intentHandler { intentRequest, session ->
                assertTrue("session-id-3064" == session.sessionId)
                throw RuntimeException("The intentHandler was invoked")
            }
        }

        assertThrows<RuntimeException> {
            val response = client.handleClovaRequest("{}", mapOf())
        }
    }

    @Test
    internal fun testSessionEndedRequestDispatch() {
        `when`(mockMapper.deserialize("{}", CustomExtensionRequest::class.java)).thenReturn(
                createMockCustomExtensionRequest(RequestType.SessionEnded))

        client = clovaClient("test", MockVerifier()) {
            objectMapper = mockMapper
            sessionEndedHandler { sessionEndedRequest, session ->
                assertTrue("session-id-3064" == session.sessionId)
                throw RuntimeException("The sessionEndedHandler was invoked")
            }
        }

        assertThrows<RuntimeException> {
            val response = client.handleClovaRequest("{}", mapOf())
        }
    }

    @BeforeEach
    internal fun setUp() {
        @SuppressWarnings("unchecked")
        mockMapper = mock(ObjectMapper::class.java) as ObjectMapper<String, String>
        `when`(mockMapper.serialize(anyString())).thenReturn("")
    }
}
