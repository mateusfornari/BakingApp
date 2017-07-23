package com.example.android.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestTablet {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
    @Test
    public void mainActivityTestTablet() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_recipe_list), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rv_steps_list),
                        withParent(withId(R.id.steps_list_fragment)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.exo_pause),
                        withContentDescription(
                                mActivityTestRule.getActivity().getString(R.string.exo_controls_pause_description)),
                        isDisplayed()));
        appCompatImageButton.perform(click());


        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.exo_play),
                        withContentDescription(
                                mActivityTestRule.getActivity().getString(R.string.exo_controls_play_description)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());



        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.exo_prev),
                        withContentDescription(
                                mActivityTestRule.getActivity().getString(R.string.exo_controls_previous_description)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());


        pressBack();



        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rv_recipe_list), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.rv_steps_list),
                        withParent(withId(R.id.steps_list_fragment)),
                        isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(2, click()));

    }

}
