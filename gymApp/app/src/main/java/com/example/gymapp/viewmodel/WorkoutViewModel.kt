package com.example.gymapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.AppDatabase
import com.example.gymapp.WorkoutSet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).workoutDao()

    // Expose data as a StateFlow for Compose [cite: 2916]
    val allSets = dao.getAllSets().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addSet(name: String, weight: String, reps: String) {
        viewModelScope.launch {
            dao.insertSet(WorkoutSet(exerciseName = name, weight = weight, reps = reps))
        }
    }

    fun deleteSet(workoutSet: WorkoutSet) {
        viewModelScope.launch {
            dao.deleteSet(workoutSet)
        }
    }
}