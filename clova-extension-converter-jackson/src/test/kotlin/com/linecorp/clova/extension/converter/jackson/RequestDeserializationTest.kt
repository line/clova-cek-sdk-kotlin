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

    private val launchRequestSample: String = "{\n" +
            "  \"version\": \"1.0\",\n" +
            "  \"session\": {\n" +
            "    \"new\": true,\n" +
            "    \"sessionAttributes\": {\"myValue\": 1234, \"myStr\": \"TEST\"},\n" +
            "    \"sessionId\": \"687dcc69-0744-41da-aa4e-a1e6fc9dec0c\",\n" +
            "    \"user\": {\n" +
            "      \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"context\": {\n" +
            "    \"System\": {\n" +
            "      \"application\": {\n" +
            "        \"applicationId\": \"idv.freddie.example.echo\"\n" +
            "      },\n" +
            "      \"device\": {\n" +
            "        \"deviceId\": \"65a9ff770ea9f4c9b46643a5612d62eacbc674d7fb56068dc1336af72864b815\",\n" +
            "        \"display\": {\n" +
            "          \"size\": \"none\",\n" +
            "          \"contentLayer\": {\n" +
            "            \"width\": 0,\n" +
            "            \"height\": 0\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"user\": {\n" +
            "        \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"request\": {\n" +
            "    \"type\": \"LaunchRequest\",\n" +
            "    \"requestId\": \"f8dcb59c-8493-4b11-a5fd-7102180f19a9\",\n" +
            "    \"timestamp\": \"2018-04-23T09:11:49Z\",\n" +
            "    \"locale\": \"ja-JP\",\n" +
            "    \"extensionId\": \"clova.example\",\n" +
            "    \"intent\": {\n" +
            "      \"intent\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"slots\": null\n" +
            "    },\n" +
            "    \"event\": {\n" +
            "      \"namespace\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"payload\": null\n" +
            "    }\n" +
            "  }\n" +
            "}"

    private val intentRequestExample: String = "{\n" +
            "  \"version\": \"0.1.0\",\n" +
            "  \"session\": {\n" +
            "    \"new\": false,\n" +
            "    \"sessionAttributes\": {},\n" +
            "    \"sessionId\": \"a29cfead-c5ba-474d-8745-6c1a6625f0c5\",\n" +
            "    \"user\": {\n" +
            "      \"userId\": \"V0qe\",\n" +
            "      \"accessToken\": \"XHapQasdfsdfFsdfasdflQQ7\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"context\": {\n" +
            "    \"System\": {\n" +
            "      \"application\": {\n" +
            "        \"applicationId\": \"com.yourdomain.extension.pizzabot\"\n" +
            "      },\n" +
            "      \"user\": {\n" +
            "        \"userId\": \"V0qe\",\n" +
            "        \"accessToken\": \"XHapQasdfsdfFsdfasdflQQ7\"\n" +
            "      },\n" +
            "      \"device\": {\n" +
            "        \"deviceId\": \"096e6b27-1717-33e9-b0a7-510a48658a9b\",\n" +
            "        \"display\": {\n" +
            "          \"size\": \"l100\",\n" +
            "          \"orientation\": \"landscape\",\n" +
            "          \"dpi\": 96,\n" +
            "          \"contentLayer\": {\n" +
            "            \"width\": 640,\n" +
            "            \"height\": 360\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"request\": {\n" +
            "    \"type\": \"IntentRequest\",\n" +
            "    \"requestId\": \"f8dcb59c-8493-4b11-a5fd-7102180f19a9\",\n" +
            "    \"timestamp\": \"2018-04-23T09:11:49Z\"," +
            "    \"locale\": \"ja-JP\"," +
            "    \"extensionId\": \"clova.example\", " +
            "    \"intent\": {\n" +
            "      \"name\": \"OrderPizza\",\n" +
            "      \"slots\": {\n" +
            "        \"pizzaType\": {\n" +
            "          \"name\": \"pizzaType\",\n" +
            "          \"value\": \"ペパロニ\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}"

    private val sessionEndedRequest: String = "{\n" +
            "  \"version\": \"1.0\",\n" +
            "  \"session\": {\n" +
            "    \"new\": false,\n" +
            "    \"sessionAttributes\": {},\n" +
            "    \"sessionId\": \"0efb3748-0ad3-46bf-9e99-541ff2aac568\",\n" +
            "    \"user\": {\n" +
            "      \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"context\": {\n" +
            "    \"System\": {\n" +
            "      \"application\": {\n" +
            "        \"applicationId\": \"idv.freddie.example.echo\"\n" +
            "      },\n" +
            "      \"device\": {\n" +
            "        \"deviceId\": \"65a9ff770ea9f4c9b46643a5612d62eacbc674d7fb56068dc1336af72864b815\",\n" +
            "        \"display\": {\n" +
            "          \"size\": \"none\",\n" +
            "          \"contentLayer\": {\n" +
            "            \"width\": 0,\n" +
            "            \"height\": 0\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"user\": {\n" +
            "        \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"request\": {\n" +
            "    \"type\": \"SessionEndedRequest\",\n" +
            "    \"requestId\": \"3168d4f5-356e-4ffa-8efd-b2efc481ed7a\",\n" +
            "    \"timestamp\": \"2018-04-23T09:39:32Z\",\n" +
            "    \"locale\": \"ja-JP\",\n" +
            "    \"reason\": \"USER_INITIATED\",\n" +
            "    \"extensionId\": \"clova.example\",\n" +
            "    \"intent\": {\n" +
            "      \"intent\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"slots\": null\n" +
            "    },\n" +
            "    \"event\": {\n" +
            "      \"namespace\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"payload\": null\n" +
            "    }\n" +
            "  }\n" +
            "}"

    private val withoutSessionAttributesExample: String = "{\n" +
            "  \"version\": \"1.0\",\n" +
            "  \"session\": {\n" +
            "    \"new\": true,\n" +
            "    \"sessionId\": \"687dcc69-0744-41da-aa4e-a1e6fc9dec0c\",\n" +
            "    \"user\": {\n" +
            "      \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"context\": {\n" +
            "    \"System\": {\n" +
            "      \"application\": {\n" +
            "        \"applicationId\": \"idv.freddie.example.echo\"\n" +
            "      },\n" +
            "      \"device\": {\n" +
            "        \"deviceId\": \"65a9ff770ea9f4c9b46643a5612d62eacbc674d7fb56068dc1336af72864b815\",\n" +
            "        \"display\": {\n" +
            "          \"size\": \"none\",\n" +
            "          \"contentLayer\": {\n" +
            "            \"width\": 0,\n" +
            "            \"height\": 0\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"user\": {\n" +
            "        \"userId\": \"durE2hnIQLaKmsMA0mQWpw\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"request\": {\n" +
            "    \"type\": \"LaunchRequest\",\n" +
            "    \"requestId\": \"f8dcb59c-8493-4b11-a5fd-7102180f19a9\",\n" +
            "    \"timestamp\": \"2018-04-23T09:11:49Z\",\n" +
            "    \"locale\": \"ja-JP\",\n" +
            "    \"extensionId\": \"clova.example\",\n" +
            "    \"intent\": {\n" +
            "      \"intent\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"slots\": null\n" +
            "    },\n" +
            "    \"event\": {\n" +
            "      \"namespace\": \"\",\n" +
            "      \"name\": \"\",\n" +
            "      \"payload\": null\n" +
            "    }\n" +
            "  }\n" +
            "}"

    @Test
    fun testLaunchRequest() {
        val request = mapper.readValue(launchRequestSample, CustomExtensionRequest::class.java)
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
        val request = mapper.readValue(intentRequestExample, CustomExtensionRequest::class.java)
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
        val request = mapper.readValue(sessionEndedRequest, CustomExtensionRequest::class.java)
        assertTrue(RequestType.SessionEnded == request.request.type)

        val sessionRequest = request.request as SessionEndedRequest
        assertTrue("USER_INITIATED" == sessionRequest.reason)
        assertTrue("ja-JP" == sessionRequest.locale)
        assertTrue("clova.example" == sessionRequest.extensionId)
        assertTrue(1524476372L == sessionRequest.timestamp)

    }

    @Test
    fun testWithoutSessionAttributes() {
        val request = mapper.readValue(withoutSessionAttributesExample, CustomExtensionRequest::class.java)
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
