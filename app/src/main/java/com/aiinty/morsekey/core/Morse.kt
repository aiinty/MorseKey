package com.aiinty.morsekey.core

class Morse {

    companion object {

        fun decode(morseCode: String, lang: Constants.MorseLanguage, isCaps: Boolean): String {
            val decoded = when(lang) {
                Constants.MorseLanguage.RU -> Constants.RU_MORSE[morseCode]
                Constants.MorseLanguage.EN -> Constants.EN_MORSE[morseCode]
            } ?: ""
            return if (isCaps) decoded.uppercase() else decoded
        }

    }

}