package com.example.gymapp

import org.junit.Assert
import org.junit.Test

class WorkoutUtilsTest {

    // A simple helper function to test logic isolated from UI
    private fun calculateVolume(weight: Int, reps: Int): Int {
        return weight * reps
    }

    @Test
    fun volume_isCorrect() {
        val result = calculateVolume(100, 5)
        Assert.assertEquals(500, result) // Assertion [cite: 2964]
    }

    @Test
    fun volume_zeroReps_returnsZero() {
        val result = calculateVolume(100, 0)
        Assert.assertEquals(0, result)
    }
}