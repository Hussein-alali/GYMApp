package com.example.gymapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale

class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get Data from Intent (Default to 90 seconds / 1:30)
        val timeSeconds = intent.getIntExtra("EXTRA_TIME", 90)

        setContent {
            TimerScreen(startTime = timeSeconds, onFinish = { finish() })
        }
    }
}

@Composable
fun TimerScreen(startTime: Int, onFinish: () -> Unit) {
    var timeLeft by remember { mutableIntStateOf(startTime) }

    // Format Seconds to "M:SS" (e.g., 90 -> "1:30")
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)

    // Coroutine for the Countdown Loop
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(2000L)
            timeLeft--
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Rest Timer", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(30.dp))

        // Row for:  [ - ]   1:30   [ + ]
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Decrease Time Button (-10s)
            FilledTonalIconButton(
                onClick = {
                    if (timeLeft > 10) timeLeft -= 10 else timeLeft = 0
                },
                modifier = Modifier.size(64.dp)
            ) {
                // Using Text "-" since we don't have the extended icons library
                Text("-", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Large Timer Display (Now showing 1:30 format)
            Text(
                text = formattedTime,
                fontSize = 80.sp,
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Increase Time Button (+10s)
            FilledTonalIconButton(
                onClick = { timeLeft += 10 },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase Time")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onFinish,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Skip Rest / Finish")
        }
    }
}