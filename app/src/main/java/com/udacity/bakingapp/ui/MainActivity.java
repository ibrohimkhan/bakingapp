package com.udacity.bakingapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.Event;
import com.udacity.bakingapp.data.common.SharedViewModel;
import com.udacity.bakingapp.ui.ingredient.IngredientFragment;
import com.udacity.bakingapp.ui.recipe.RecipeFragment;
import com.udacity.bakingapp.ui.recipelist.RecipeListFragment;
import com.udacity.bakingapp.ui.step.StepFragment;

import butterknife.BindBool;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVE_FRAGMENT = "active_fragment";

    private SharedViewModel sharedViewModel;

    private Fragment activeFragment;

    @BindBool(R.bool.tablet_mode)
    boolean isTabletMode;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStackImmediate();

            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();

            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        if (savedInstanceState != null) {
            String tag = savedInstanceState.getString(ACTIVE_FRAGMENT);
            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

            if (activeFragment instanceof RecipeListFragment)
                showRecipeListFragment();
            else if (activeFragment instanceof RecipeFragment)
                showRecipeFragment();
            else if (activeFragment instanceof IngredientFragment)
                showIngredientFragment();
            else if (activeFragment instanceof StepFragment)
                showStepFragment();

        } else {
            showRecipeListFragment();
        }

        sharedViewModel.getSelectedRecipe().observe(this, recipe -> {
            if (recipe.getIfNotHandled() == null) return;
            showRecipeFragment();

            if (isTabletMode)
                sharedViewModel.selectedIngredients(new Event<>(recipe.peek().ingredients));
        });

        sharedViewModel.getSelectedIngredients().observe(this, ingredients -> {
            if (ingredients.getIfNotHandled() == null) return;

            if (!isTabletMode) showIngredientFragment();
            else replaceFragment(IngredientFragment.newInstance());
        });

        sharedViewModel.getStep().observe(this, step -> {
            if (step.getIfNotHandled() == null) return;

            if (!isTabletMode) showStepFragment();
            else replaceFragment(StepFragment.newInstance());
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT, activeFragment.getTag());
    }

    private void showStepFragment() {
        if (activeFragment instanceof StepFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(StepFragment.TAG);

        if (fragment == null) {
            fragment = StepFragment.newInstance();
            addNewFragment(fragment, StepFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showIngredientFragment() {
        if (activeFragment instanceof IngredientFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(IngredientFragment.TAG);

        if (fragment == null) {
            fragment = IngredientFragment.newInstance();
            addNewFragment(fragment, IngredientFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showRecipeFragment() {
        if (activeFragment instanceof RecipeFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RecipeFragment.TAG);

        if (fragment == null) {
            fragment = RecipeFragment.newInstance();
            addNewFragment(fragment, RecipeFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showRecipeListFragment() {
        if (activeFragment instanceof RecipeListFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RecipeListFragment.TAG);

        if (fragment == null) {
            fragment = RecipeListFragment.newInstance();
            addNewFragment(fragment, RecipeListFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void addNewFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null) transaction.hide(activeFragment);

        transaction.add(R.id.fragment_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        activeFragment = fragment;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null)
            transaction.hide(activeFragment);

        transaction.show(fragment);
        transaction.commitNow();

        activeFragment = fragment;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_detail_container, fragment).commit();
    }
}
