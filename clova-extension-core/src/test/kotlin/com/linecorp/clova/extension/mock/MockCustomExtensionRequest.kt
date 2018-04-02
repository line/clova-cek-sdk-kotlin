/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.mock

import com.linecorp.clova.extension.model.core.Application
import com.linecorp.clova.extension.model.core.Context
import com.linecorp.clova.extension.model.core.Device
import com.linecorp.clova.extension.model.core.Display
import com.linecorp.clova.extension.model.core.System
import com.linecorp.clova.extension.model.core.User
import com.linecorp.clova.extension.model.request.CustomExtensionRequest
import com.linecorp.clova.extension.model.request.CustomRequest
import com.linecorp.clova.extension.model.request.Intent
import com.linecorp.clova.extension.model.request.IntentRequest
import com.linecorp.clova.extension.model.request.LaunchRequest
import com.linecorp.clova.extension.model.request.RequestType
import com.linecorp.clova.extension.model.request.Session
import com.linecorp.clova.extension.model.request.SessionEndedRequest

fun createMockCustomExtensionRequest(requestType: RequestType): CustomExtensionRequest {
    val user = User("userId", "accessToken")
    val request: CustomRequest = when(requestType) {
        RequestType.Launch -> LaunchRequest(
                id = "request-id",
                timestamp = 1234567,
                locale = "ja-JP",
                extensionId = "extension-id"
        )
        RequestType.Intent -> IntentRequest(
                timestamp = 1234567,
                locale = "ja-JP",
                extensionId = "extension-id",
                intent = Intent("test", slots = mapOf())
        )
        RequestType.SessionEnded -> SessionEndedRequest(id = "request-id",
                timestamp = 1234567,
                locale = "ja-JP",
                extensionId = "extension-id",
                reason = "test")
    }
    return CustomExtensionRequest(
            version = "1.0",
            session = Session(
                    isNew = true,
                    sessionAttributes = mapOf(),
                    sessionId = "session-id-3064",
                    user = user
            ),
            context = Context(
                    system = System(
                            application = Application("sdk.test"),
                            user = user,
                            device = Device(id = "device-id", display = Display())
                    )
            ),
            request = request
    )
}
