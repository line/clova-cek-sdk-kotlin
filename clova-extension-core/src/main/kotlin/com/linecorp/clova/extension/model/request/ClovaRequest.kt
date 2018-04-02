package com.linecorp.clova.extension.model.request

import com.linecorp.clova.extension.model.core.Context

/**
 * The basic type of clova request
 */
open class ClovaRequest

/**
 * The custom extension request
 */
data class CustomExtensionRequest(
        val version: String,
        val session: Session,
        val context: Context,
        val request: CustomRequest
) : ClovaRequest()
