/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.mock

import com.linecorp.clova.extension.client.RequestVerifier

/**
 * For test purpose, do not use it in release version.
 */
class MockVerifier : RequestVerifier {
    override fun verify(signature: String, requestBody: String): Boolean = true
}
