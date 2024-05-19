package com.example.todoapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Test

class LoginActivityTest {

    @Test
    fun loginSuccess() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.etUser)).check(matches(isDisplayed()))
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()))

        onView(withId(R.id.etUser)).perform(replaceText("username"))
        onView(withId(R.id.etPassword)).perform(replaceText("password"))

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.mainLay)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun loginFailureEmptyFields() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.etUser)).check(matches(isDisplayed()))
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()))

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText("Please fill in all required fields.")).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun signUpNavigation() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.etUser)).check(matches(isDisplayed()))
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()))

        onView(withId(R.id.btnSignUp)).perform(click())

        onView(withId(R.id.signUpLay)).check(matches(isDisplayed()))

        activityScenario.close()
    }
}