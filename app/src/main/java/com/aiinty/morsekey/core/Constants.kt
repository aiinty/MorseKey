package com.aiinty.morsekey.core

class Constants {

    enum class MorseLanguage {
        RU,
        EN
    }

    companion object {

        val RU_MORSE = mapOf(
            "а" to ".-", "б" to "-...", "в" to ".--", "г" to "--.", "д" to "-..",
            "е" to ".", "ж" to "...-", "з" to "--..", "и" to "..", "й" to ".---",
            "к" to "-.-", "л" to ".-..", "м" to "--", "н" to "-.", "о" to "---",
            "п" to ".--.", "р" to ".-.", "с" to "...", "т" to "-", "у" to "..-",
            "ф" to "..-.", "х" to "....", "ц" to "-.-.", "ч" to "---.", "ш" to "----",
            "щ" to "--.-", "ъ" to "--.--", "ы" to "-.--", "ь" to "-..-", "э" to "..-..",
            "ю" to "..--", "я" to ".-.-",
            "." to "......", "," to ".-.-.-", ":" to "---...", ";" to "-.-.-.", "'" to ".----.",
            "\"" to ".-..-.", "-" to "-....-", "/" to "-..-.", "_" to "..--.-", "?" to "..--..",
            "!" to "--..--", "+" to ".-.-.",
        )

        val EN_MORSE = mapOf(
            "" to ""
            // TODO:
        )

    }

}