package com.linecorp.clova.extension.model.response

/**
 * The type of [SpeechInfo]
 */
enum class SpeechInfoType {
    PlainText,
    Url,
    SpeechList; //for SpeechSet

    override fun toString(): String = when(this) {
        PlainText -> "PlainText"
        Url -> "URL"
        SpeechList -> "SpeechList"
    }

    companion object {
        @JvmStatic
        fun of(value: String): SpeechInfoType = when (value.toLowerCase()) {
            "plaintext" -> PlainText
            "url" -> Url
            "speechlist" -> SpeechList
            else -> throw IllegalArgumentException("couldn't covert $value to SpeechInfoType")
        }
    }
}

/**
 * The supported languages, Clova only supports Japanese, English, Korean.
 */
enum class SupportedLanguage {
    JA,
    EN,
    KO,
    NONE; //for type URL

    override fun toString(): String {
        return super.toString().toLowerCase()
    }

    companion object {
        @JvmStatic
        fun of(value: String): SupportedLanguage = when(value.toLowerCase()) {
            "ja" -> JA
            "en" -> EN
            "ko" -> KO
            else -> NONE
        }
    }
}

/**
 * The definition of the basic voice response.
 *
 * @property type the type of speech info
 * @property lang Supported languages, If the type is [SpeechInfoType.Url], this value would be NONE.
 * @property value the response message
 */
data class SpeechInfo (
        val type: SpeechInfoType,
        val lang: SupportedLanguage,
        val value: String
)
