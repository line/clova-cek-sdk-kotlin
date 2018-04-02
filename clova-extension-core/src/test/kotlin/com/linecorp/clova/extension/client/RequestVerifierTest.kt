/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.client

import com.linecorp.clova.extension.client.ClovaClient.Companion.HEADER_SIGNATURE
import com.linecorp.clova.extension.mock.MockVerifier
import com.linecorp.clova.extension.mock.createMockCustomExtensionRequest
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.RequestType
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class RequestVerifierTest {

    @Test
    fun testSignatureOk() {
        assertTrue(RequestVerifierImpl(PUBLIC_KEY_FOR_TEST).verify(GOLDEN_SIGNATURE, GOLDEN_REQUEST_BODY))
    }

    @Test
    fun testSignatureNG() {
        assertFalse(RequestVerifierImpl(PUBLIC_KEY_FOR_TEST).verify(GOLDEN_SIGNATURE, REQUEST_BODY_EXAMPLE))
    }

    @Test
    fun testApplicationId() {
        @SuppressWarnings("unchecked")
        val mockMapper = Mockito.mock(ObjectMapper::class.java) as ObjectMapper<String, String>
        `when`(mockMapper.deserialize(GOLDEN_REQUEST_BODY, CustomExtensionRequest::class.java)).thenReturn(
                createMockCustomExtensionRequest(RequestType.Launch))

        val client = clovaClient("sdk.test", MockVerifier()) {
            objectMapper = mockMapper
            launchHandler { launchRequest, session ->
                throw RuntimeException("The launchHandler was invoked")
            }
        }
        val exception = assertThrows<RuntimeException> {
            client.handleClovaRequest(GOLDEN_REQUEST_BODY, mapOf(HEADER_SIGNATURE to listOf(GOLDEN_SIGNATURE)))
        }
        assertTrue("The launchHandler was invoked" == exception.message)
    }

    @Test
    fun testIncorrectApplicationId() {
        @SuppressWarnings("unchecked")
        val mockMapper = Mockito.mock(ObjectMapper::class.java) as ObjectMapper<String, String>
        `when`(mockMapper.deserialize(GOLDEN_REQUEST_BODY, CustomExtensionRequest::class.java)).thenReturn(
                createMockCustomExtensionRequest(RequestType.Launch))

        val client = clovaClient("sdk.test123", MockVerifier()) {
            objectMapper = mockMapper
            launchHandler { launchRequest, session ->
                throw RuntimeException("The launchHandler was invoked")
            }
        }
        val exception = assertThrows<RuntimeException> {
            client.handleClovaRequest(GOLDEN_REQUEST_BODY, mapOf(HEADER_SIGNATURE to listOf(GOLDEN_SIGNATURE)))
        }
        assertTrue("The request is illegal" == exception.message)
    }

    companion object {
        const val GOLDEN_SIGNATURE = "nXQcmPWHrVhLn1suNOdYycERQi5o5xDnt0NcZGvZWSukq8YOfd/ABNQOPTHQdmpax/NYrXohkmv" +
                "ZxVah5Yrn1rfJBG6LNjf1L8MsUx0H+b0bzIuiXv18PS4xb6lXT/TwJ9xRmZtECYlbu8Ut6CvY70xedDDBq64O1hMSDxXAdK9" +
                "G0Nco7gMH67cnsvoJy64Of9Pwu7zhAi48fwg7xKkre7rSW3UbF4uBjhZYqNLks3tMhF2ow3onQl10eUi8id53wOADlQ+K1JC" +
                "wSldZu5sHKG2PnbhU6o9niKHHrglpBW+FPrE/4A24tu1sR7NzsZLqX8FqmrFZOYmAdoRMLJssPQ=="

        const val GOLDEN_REQUEST_BODY = "{\"version\":\"1.0\"}"

        //only change version number
        const val REQUEST_BODY_EXAMPLE = "{\"version\":\"2.0\"}"

        private const val PUBLIC_KEY_FOR_TEST = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1wB6kjCsUNAyq6MzdQ4H
l5p79Qek13KOWumx5wR8/NEqQK0j/Wh9LcLLmIJnN+SVoKEV/Nyfa13flDnNTxkL
pjqPORnSyvQQf/avTEWvIq6SUOIb0UqJT6JV3HggaZX259DyB0/jfj63i3A/Jx5w
+EVFGnR6RKQjVC6v1Nz7BYZE4LYfabtt5KRGjPoFcJvwXapDi6VkPA9xSBz5kYvo
yzx4SQRtFDXRCcikwonSdrD97KNkC2kPEl1UYusgOGO0uywY1z8bs+TQwDt4cxOw
zUF/WSFG1+UWkihT1WxSYmrY9hon4ROcyadaoXyEVm8VsKkNI1yllAito/Hci0R+
3wIDAQAB
-----END PUBLIC KEY-----"""
    }
}
