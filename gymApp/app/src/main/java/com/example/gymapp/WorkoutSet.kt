package com.example.gymapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseName: String,
    val weight: String,
    val reps: String
)