package com.linecorp.clova.extension.exception

import java.io.IOException

/**
 * Signals that the mandatory JSON properties are missing.
 */
class MissingJsonPropertyException(message: String) : IOException(message)
