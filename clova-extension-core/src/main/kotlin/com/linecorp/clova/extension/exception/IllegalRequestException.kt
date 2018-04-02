/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.exception

import java.lang.RuntimeException

/**
 * Signals that the Clova responses are illegal.
 */
class IllegalRequestException : RuntimeException("The request is illegal")
