package com.aiinty.morsekey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aiinty.morsekey.ui.theme.MorseKeyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorseKeyTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        val text = remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            label = { Text("Test here...") },
                        )
                    }

                }
            }
        }
    }
}
