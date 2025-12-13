package com.example.gymapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.WorkoutSet
import com.example.gymapp.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitTimerApp()
        }
    }
}

@Composable
fun FitTimerApp(viewModel: WorkoutViewModel = viewModel()) {
    // Collect the Flow from Room as Compose State [cite: 2877]
    val workoutList by viewModel.allSets.collectAsState()

    // Mutable state for input fields [cite: 2880]
    var exercise by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "FitTimer ", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(10.dp))

        // Inputs
        OutlinedTextField(
            value = exercise,
            onValueChange = { exercise = it },
            label = { Text("Exercise (e.g. Squat)") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Reps") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ADD BUTTON [cite: 2875]
        Button(
            onClick = {
                if (exercise.isNotEmpty()) {
                    viewModel.addSet(exercise, weight, reps)
                    // Reset fields
                    exercise = ""
                    weight = ""
                    reps = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Set")
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()

        // LIST OF WORKOUTS [cite: 2913]
        LazyColumn {
            items(workoutList) { set ->
                WorkoutItem(set, onDelete = { viewModel.deleteSet(set) })
            }
        }
    }
}

@Composable
fun WorkoutItem(set: WorkoutSet, onDelete: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = set.exerciseName, style = MaterialTheme.typography.titleMedium)
                Text(text = "${set.weight} kg x ${set.reps} reps")
            }


            IconButton(onClick = {
                val intent = Intent(context, TimerActivity::class.java)
                intent.putExtra("EXTRA_TIME", 60) // Passing data [cite: 2061]
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start Timer")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}