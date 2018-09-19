package com.linecorp.clova.extension.model.core

import com.linecorp.clova.extension.model.audio.AudioPlayer

data class Context(
        val system: System,
        val audioPlayer: AudioPlayer? = null
)
