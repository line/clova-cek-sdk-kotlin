package com.linecorp.clova.extension.exception

import java.lang.RuntimeException

/**
 * Signals that the non-supported requests are received.
 */
class NonsupportedRequestTypeException(message: String) : RuntimeException(message)
