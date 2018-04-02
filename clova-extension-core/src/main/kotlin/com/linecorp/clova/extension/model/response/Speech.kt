package com.linecorp.clova.extension.model.response

import com.linecorp.clova.extension.Experimental

enum class SpeechType {
    SimpleSpeech,
    SpeechList,
    SpeechSet
}

sealed class Speech

data class SimpleSpeech(val values: SpeechInfo) : Speech()

data class SpeechList(val values: List<SpeechInfo>) : Speech()

@Experimental
data class SpeechSet(val values: Map<String, Any>) : Speech()
