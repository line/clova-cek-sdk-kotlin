package com.linecorp.clova.extension.client

import com.linecorp.clova.extension.API_VERSION
import com.linecorp.clova.extension.exception.IllegalRequestException
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.request.ClovaRequest
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.EventRequest
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.Session
import com.linecorp.clova.extension.model.request.SessionEndedRequest
import com.linecorp.clova.extension.model.response.ClovaExtensionResponse
import com.linecorp.clova.extension.model.util.simpleResponse
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * The generic interface for serialize/deserialize the object.
 * To implement this interface to customize the object mapper.
 */
interface ObjectMapper<in InType, out OutType> {
    /**
     * Deserialize function for a object
     *
     * @param value any input value
     * @param cls the class type for input object
     * @return the deserialization result
     */
    fun <V: ClovaRequest> deserialize(value: InType, cls: Class<V>): V

    /**
     * Serialize function for a object
     *
     * @param obj the input
     * @return the serialization result
     */
    fun serialize(obj: Any): OutType
}

/**
 * The alias of request handler. It is suspend function with two parameters (T, <code>Session</code>)
 */
typealias RequestHandler<T> = suspend (T, Session) -> ClovaExtensionResponse

/**
 * The <code>ClovaClient</code> provides the functionality for handling the requests from Clova platform,
 * SDK also provides a DSL [(example)][clovaClient] to create [ClovaClient]
 *
 * @param applicationId The application id, It must be the same as the one registered in Clova Developer Center.
 * @param verifier The request verifier, default is using the built-in request verifier
 */
class ClovaClient(private val applicationId: String, private val verifier: RequestVerifier = RequestVerifierImpl()) {

    private val logger: Logger = LoggerFactory.getLogger("clova-client")

    /**
     * The SDK version that client supports
     */
    val clientVersion: String = API_VERSION
    private var serverVersion: String = API_VERSION

    /**
     * The object mapper to convert json to <code>ClovaRequest</code> object
     */
    var objectMapper: ObjectMapper<String, String> = DefaultObjectMapper()

    internal var launchHandler: RequestHandler<LaunchRequest>? = null
    internal var intentHandler: RequestHandler<IntentRequest>? = null
    internal var sessionEndedHandler: RequestHandler<SessionEndedRequest>? = null
    internal var eventHandler: RequestHandler<EventRequest>? = null

    /**
     * Handle the custom extension request synchronizedly
     *
     * @param requestBody the requestBody from clova. It supposed to be json string.
     * @param headers the HTTP headers. Since the field name is case-insensitive, it would be treated as lowercase in this function.
     * @return the response string in json format
     */
    fun handleClovaRequest(requestBody: String, headers: Map<String, List<String>>): String {

        val customExtensionRequest = toCustomExtensionRequest(headers, requestBody)
        val response = when(customExtensionRequest.request) {
            is LaunchRequest -> runBlocking {
                handleLaunchRequest(
                        customExtensionRequest.session,
                        customExtensionRequest.context,
                        customExtensionRequest.request
                )
            }
            is IntentRequest -> runBlocking {
                handleIntentRequest(
                        customExtensionRequest.session,
                        customExtensionRequest.context,
                        customExtensionRequest.request
                )
            }
            is SessionEndedRequest -> runBlocking {
                handleSessionEndRequest(
                        customExtensionRequest.session,
                        customExtensionRequest.context,
                        customExtensionRequest.request
                )
            }
            is EventRequest -> runBlocking {
                handleEventRequest(
                        customExtensionRequest.session,
                        customExtensionRequest.context,
                        customExtensionRequest.request
                )
            }
        }

        return objectMapper.serialize(response)
    }

    /**
     * Handle the custom extension request with suspend support
     *
     * @param requestBody the requestBody from clova. It supposed to be json string.
     * @param headers the HTTP headers. Since the field name is case-insensitive, it would be treated as lowercase in this function.
     * @return the response string in json format
     */
    suspend fun handleClovaRequestSuspend(requestBody: String, headers: Map<String, List<String>>): String {

        val customExtensionRequest = toCustomExtensionRequest(headers, requestBody)

        val response = when(customExtensionRequest.request) {
            is LaunchRequest -> handleLaunchRequest(
                    customExtensionRequest.session,
                    customExtensionRequest.context,
                    customExtensionRequest.request
            )
            is IntentRequest -> handleIntentRequest(
                    customExtensionRequest.session,
                    customExtensionRequest.context,
                    customExtensionRequest.request
            )
            is SessionEndedRequest -> handleSessionEndRequest(
                    customExtensionRequest.session,
                    customExtensionRequest.context,
                    customExtensionRequest.request
            )

            is EventRequest -> handleEventRequest(
                    customExtensionRequest.session,
                    customExtensionRequest.context,
                    customExtensionRequest.request
            )
        }

        return objectMapper.serialize(response)
    }

    @Throws(IllegalRequestException::class)
    private fun toCustomExtensionRequest(headers: Map<String, List<String>>, requestBody: String): CustomExtensionRequest {

        val signature = headers.getFirstValue(HEADER_SIGNATURE.toLowerCase())
        if (!verifier.verify(signature, requestBody)) {
            throw IllegalRequestException()
        }

        val customExtensionRequest = objectMapper.deserialize(
                requestBody,
                CustomExtensionRequest::class.java
        )

        if (!checkVersion(customExtensionRequest.version)) {
            logger.warn("client api version($clientVersion) doesn't match server api version(" +
                    "${customExtensionRequest.version})")
        }

        // separate the check for unit tests because it is impossible to create valid request body and header by ourselves.
        if (this.applicationId == customExtensionRequest.context.system.application?.id) {
            return customExtensionRequest
        } else {
            logger.error("The applicationId (${this.applicationId}) doesn't match the application id " +
                    "${customExtensionRequest.context.system.application?.id} in the request")
            throw IllegalRequestException()
        }
    }

    private fun checkVersion(serverApiVersion: String): Boolean = clientVersion == serverApiVersion

    private suspend fun handleLaunchRequest(
            session: Session,
            context: Context,
            request: LaunchRequest
    ): ClovaExtensionResponse =
        withContext(DefaultDispatcher) {
            launchHandler?.invoke(request, session) ?: defaultResponse
        }

    private suspend fun handleIntentRequest(
            session: Session,
            context: Context,
            request: IntentRequest
    ): ClovaExtensionResponse =
        withContext(DefaultDispatcher) {
            intentHandler?.invoke(request, session) ?: defaultResponse
        }

    private suspend fun handleSessionEndRequest(
            session: Session,
            context: Context,
            request: SessionEndedRequest
    ): ClovaExtensionResponse =
        withContext(DefaultDispatcher) {
            sessionEndedHandler?.invoke(request, session) ?: defaultResponse
        }

    private suspend fun handleEventRequest(
            session: Session,
            context: Context,
            request: EventRequest
    ): ClovaExtensionResponse =
        withContext(DefaultDispatcher) {
            eventHandler?.invoke(request, session) ?: defaultResponse
        }


    private fun Map<String, List<String>>.getFirstValue(name: String): String =
        this[name]?.let { if (it.isNotEmpty()) it.first() else "" } ?: ""

    companion object {
        private val defaultResponse = simpleResponse("")
        internal const val HEADER_SIGNATURE: String = "SignatureCEK"
    }
}

internal class DefaultObjectMapper : ObjectMapper<String, String> {
    override fun <V : ClovaRequest> deserialize(value: String, cls: Class<V>): V =
            TODO("Should not use default object mapper")

    override fun serialize(obj: Any): String =
            TODO("Should not use default object mapper")
}
