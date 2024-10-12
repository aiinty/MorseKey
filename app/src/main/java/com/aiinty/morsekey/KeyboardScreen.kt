package com.aiinty.morsekey

import android.content.Context
import android.content.res.Resources.Theme
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.morsekey.core.Constants
import com.aiinty.morsekey.core.Morse

@Preview
@Composable
fun KeyboardScreen() {
    Column(
        modifier = Modifier.background(Color(0xFFEAEAEA)).fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {
        val code = remember { mutableStateOf("") }
        val lang = remember { mutableStateOf(Constants.MorseLanguage.RU) }
        val isCaps = remember { mutableStateOf(false) }

        Row (
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = code.value,
                onValueChange = { code.value = it },
                readOnly = true,
                modifier = Modifier.padding(start = 2.dp).weight(6f)
            )

            FixedHeightBox(
                Modifier.fillMaxWidth().weight(1f),
                height = 58.dp
            ) {
                KeyboardKey(keyboardKey = "<-", modifier = Modifier.weight(1f), 16.sp) { ctx: Context, _: String ->
                    if (code.value.isEmpty()) {
                        (ctx as IMEService).currentInputConnection.sendKeyEvent(
                            KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)
                        )
                    } else {
                        code.value = code.value.dropLast(1)
                    }
                }
            }
        }

        FixedHeightBox(
            Modifier.fillMaxWidth(),
            height = 112.dp
        ) {
            Row {
                KeyboardKey(keyboardKey = ".", modifier = Modifier.weight(1f), 64.sp) { _: Context, key: String ->
                    code.value += key
                }
                KeyboardKey(keyboardKey = "-", modifier = Modifier.weight(1f), 64.sp) { _: Context, key: String ->
                    code.value += key
                }
            }
        }

        FixedHeightBox(
            Modifier.fillMaxWidth(),
            height = 52.dp
        ) {
            Row {
                KeyboardKey(keyboardKey = lang.value.name, modifier = Modifier.weight(1f), 16.sp) { _: Context, _: String ->
                    lang.value = when (lang.value) {
                        Constants.MorseLanguage.RU -> Constants.MorseLanguage.EN
                        Constants.MorseLanguage.EN -> Constants.MorseLanguage.RU
                    }
                }

                KeyboardKey(
                    keyboardKey = when(isCaps.value) { true -> "↟" false -> "↑" },
                    modifier = Modifier.weight(1f),
                    14.sp
                ) { _: Context, _: String ->
                    isCaps.value = !isCaps.value
                }

                KeyboardKey(
                    keyboardKey = "SPACE",
                    modifier = Modifier.weight(3f),
                    16.sp
                ) { ctx: Context, key: String ->
                    (ctx as IMEService).currentInputConnection.commitText(" ", key.length)
                }

                KeyboardKey(
                    keyboardKey = "↩",
                    modifier = Modifier.weight(1f), 14.sp
                ) { ctx: Context, _: String ->
                    if (code.value.isEmpty()){
                        (ctx as IMEService).currentInputConnection.sendKeyEvent(
                            KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)
                        )
                    } else {
                        val res = Morse.decode(code.value, lang.value, isCaps.value)
                        (ctx as IMEService).currentInputConnection.commitText(res, res.length)
                        code.value = ""
                    }
                }
            }
        }
    }
}

@Composable
fun FixedHeightBox(modifier: Modifier, height: Dp, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val h = height.roundToPx()
        layout(constraints.maxWidth, h) {
            placeables.forEach { placeable ->
                placeable.place(x = 0, y = kotlin.math.min(0, h - placeable.height))
            }
        }
    }
}

@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier,
    fontSize: TextUnit,
    onClick: (Context, String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
        val shape = RoundedCornerShape(10)
        Text(
            keyboardKey,
            Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .border(1.dp, Color.Black, shape)
                .clickable(interactionSource = interactionSource, indication = null) {
                    onClick(context, keyboardKey)
                }
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )
        if (pressed.value) {
            Text(
                keyboardKey,
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, shape)
                    .clip(shape)
                    .background(Color.Gray)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 48.dp
                    ),
                fontSize = fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
}
