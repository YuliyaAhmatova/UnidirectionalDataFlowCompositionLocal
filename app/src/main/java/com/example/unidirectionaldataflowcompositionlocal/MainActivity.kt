package com.example.unidirectionaldataflowcompositionlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator()
        }
    }
}

@Composable
fun Calculator() {
    val heightState = rememberSaveable() {
        mutableStateOf(0)
    }
    val weightState = rememberSaveable() {
        mutableStateOf(0)
    }

    val coefficientState = rememberSaveable() {
        mutableStateOf("")
    }

    val interpretationState = rememberSaveable() {
        mutableStateOf("Выраженный дефицит массы тела")
    }

    val imtState = rememberSaveable() {
        mutableStateOf(0.0)
    }

    fun updateInterpretation() {
        interpretationState.value = when {
            imtState.value <= 16.0 -> "Выраженный дефицит массы тела"
            imtState.value in 16.0..18.5 -> "Недостаточная масса тела"
            imtState.value in 18.5..25.0 -> "Нормальная масса тела"
            imtState.value in 25.0..30.0 -> "Избыточная масса тела (предожирение)"
            imtState.value in 30.0..35.0 -> "Ожирение 1-ой степени"
            imtState.value in 35.0..40.0 -> "Ожирение 2-ой степени"
            imtState.value >= 40.0 -> "Ожирение 3-ей степени"
            else -> ""
        }
    }

    fun updateIMT() {
        val heightInMeters = heightState.value.toDouble() / 100.0
        imtState.value = if (heightInMeters > 0.0) {
            (weightState.value.toDouble() / (heightInMeters * heightInMeters))
        } else {
            0.0
        }
        updateInterpretation()
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Калькулятор ИМТ",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(5.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "Рост:",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Row {
                Text(
                    text = "${heightState.value}",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                heightState.value += 5
                                updateIMT()
                            }
                        )
                )
                Text(
                    text = " см",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "Вес:",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Row {
                Text(
                    text = "${weightState.value}",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                weightState.value += 5
                                updateIMT()
                            }
                        )
                )
                Text(
                    text = " кг",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "Коэффициент ИМТ:",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${imtState.value}",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(5.dp),
                text = interpretationState.value,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable(
                        onClick = {
                            heightState.value = 0
                            weightState.value = 0
                            updateIMT()
                        }
                    )
                ,
                text = "Сбросить",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}