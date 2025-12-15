package com.example.gymapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class FitTimerUITest {

    // 1. This Rule launches the App (MainActivity) before every test
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addWorkout_appearsInList() {
        // --- ARRANGE & ACT: Type data into the UI ---

        // Find the "Exercise" text field and type "Espresso Bench Press"
        composeTestRule.onNodeWithText("Exercise (e.g. Squat)")
            .performTextInput("Espresso Bench Press")

        // Find the "Weight" field and type "100"
        composeTestRule.onNodeWithText("Weight")
            .performTextInput("100")

        // Find the "Reps" field and type "10"
        composeTestRule.onNodeWithText("Reps")
            .performTextInput("10")

        // Click the "Log Set" button
        composeTestRule.onNodeWithText("Log Set")
            .performClick()

        // --- ASSERT: Check if the UI updated correctly ---

        // Check if our new item is displayed on the screen
        composeTestRule.onNodeWithText("Espresso Bench Press")
            .assertIsDisplayed()

        // Check if the weight/reps detail text exists
        composeTestRule.onNodeWithText("100 kg x 10 reps")
            .assertIsDisplayed()
    }
}