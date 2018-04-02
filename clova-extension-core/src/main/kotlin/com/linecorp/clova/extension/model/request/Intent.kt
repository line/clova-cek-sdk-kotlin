package com.linecorp.clova.extension.model.request

data class Intent(
        val name: String,
        val slots: Map<String, SlotValue>
)

data class SlotValue(
        val name: String,
        val value: String
)
