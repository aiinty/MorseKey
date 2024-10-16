package com.aiinty.morsekey.core

/**
 * A class for working with morse.
 */
class Morse {

    companion object {
        /**
         * Decodes a single [morseCode] in a specific [lang].
         * @return character equal to [morseCode] of a certain [lang].
         */
        fun decode(morseCode: String, lang: Constants.MorseLanguage, isCaps: Boolean): String {
            val decoded = when(lang) {
                Constants.MorseLanguage.RU -> Constants.RU_MORSE[morseCode]
                Constants.MorseLanguage.EN -> Constants.EN_MORSE[morseCode]
            } ?: ""
            return if (isCaps) decoded.uppercase() else decoded
        }
    }
}