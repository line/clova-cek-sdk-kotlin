package com.linecorp.clova.extension.model.core

data class System(
        val application: Application? = null,
        val user: User,
        val device: Device
)
