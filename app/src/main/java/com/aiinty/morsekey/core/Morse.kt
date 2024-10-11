package com.aiinty.morsekey.core

class Morse {

    companion object {

        fun decode(morseCode: String, lang: Constants.MorseLanguage): String {
            return when(lang) {
                    Constants.MorseLanguage.RU -> Constants.RU_MORSE[morseCode]
                    Constants.MorseLanguage.EN -> Constants.EN_MORSE[morseCode]
            } ?: ""
        }

    }

}