package com.udacity.bakingapp;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.udacity.bakingapp.ui.recipe.RecipeFragment;
import com.udacity.bakingapp.ui.recipelist.RecipeListFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SimpleRecipeUITest {

    private CountingIdlingResource idlingResource;

    @Before
    public void setUp() {
        FragmentScenario<RecipeListFragment> scenario
                = FragmentScenario.launchInContainer(RecipeListFragment.class);

        scenario.onFragment(fragment -> {
            idlingResource = fragment.getIdlingResource();
        });
    }

    @Test
    public void checkInitialUI() {
        onView(withId(R.id.pb_loader)).check(matches(isDisplayed()));

        if (idlingResource != null) {
            IdlingRegistry.getInstance().register(idlingResource);
        }

        onView(withId(R.id.pb_loader)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
    }

    @Test
    public void checkRecipeListUI() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().register(idlingResource);
        }

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        FragmentScenario.launchInContainer(RecipeFragment.class);
        onView(withId(R.id.tv_recipe_title)).check(matches(isDisplayed()));
    }

    @After
    public void unregister() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
