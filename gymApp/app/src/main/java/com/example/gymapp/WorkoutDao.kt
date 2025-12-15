package com.example.gymapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertSet(workoutSet: WorkoutSet) // Suspend to run off main thread [cite: 2795]

    @Delete
    suspend fun deleteSet(workoutSet: WorkoutSet)

    @Query("SELECT * FROM workout_table ORDER BY id DESC")
    fun getAllSets(): Flow<List<WorkoutSet>> // Flow updates UI automatically
}