package com.example.todoapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class SignUpActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun signUpWithValidCredentials() {
        onView(withId(R.id.etName)).perform(typeText("John Doe"), closeSoftKeyboard())
        onView(withId(R.id.etUsername)).perform(typeText("johndoe"), closeSoftKeyboard())
        onView(withId(R.id.etSignUpPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.etRepeatPassword)).perform(typeText("password"), closeSoftKeyboard())

        onView(withId(R.id.btnSign)).perform(click())

        onView(withId(R.id.mainLay)).check(matches(isDisplayed()))
    }

    @Test
    fun signUpWithMismatchedPasswords() {
        onView(withId(R.id.etName)).perform(typeText("John Doe"), closeSoftKeyboard())
        onView(withId(R.id.etUsername)).perform(typeText("johndoe"), closeSoftKeyboard())
        onView(withId(R.id.etSignUpPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.etRepeatPassword)).perform(typeText("differentpassword"), closeSoftKeyboard())

        onView(withId(R.id.btnSign)).perform(click())

        onView(withText("Passwords do not match.")).check(matches(isDisplayed()))
    }

    @Test
    fun signUpWithEmptyFields() {
        onView(withId(R.id.btnSign)).perform(click())

        onView(withText("Please fill in all required fields.")).check(matches(isDisplayed()))
    }
}