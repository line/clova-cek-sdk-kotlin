/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.converter.jackson

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class JsonResources(filename: String) {

    private val uri = javaClass.classLoader.getResource(filename).toURI()

    fun load(): String = String(Files.readAllBytes(Paths.get(uri)), Charset.forName("utf-8"))
}
