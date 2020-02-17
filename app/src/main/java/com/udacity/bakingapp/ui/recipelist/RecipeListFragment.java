package com.udacity.bakingapp.ui.recipelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.common.SharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    public static final String TAG = "RecipeListFragment";

    // The Idling Resource which will be null in production.
    private CountingIdlingResource idlingResource = new CountingIdlingResource(TAG);

    private SharedViewModel sharedViewModel;
    private RecipeListViewModel viewModel;

    private RecipeListAdapter adapter;

    @BindView(R.id.pb_loader)
    ProgressBar progressBar;

    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;

    public static RecipeListFragment newInstance() {
        Bundle args = new Bundle();

        RecipeListFragment fragment = new RecipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
            viewModel = new ViewModelProvider(getActivity()).get(RecipeListViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count)));

        viewModel.recipes.observe(this, recipes -> {
            adapter = new RecipeListAdapter(recipes, viewModel);
            recyclerView.setAdapter(adapter);
        });

        viewModel.recipe.observe(this, recipe -> {
            sharedViewModel.selectedRecipe(recipe);
        });

        viewModel.loading.observe(this, loading -> {
            if (loading.getIfNotHandled() == null) return;

            if (loading.peek()) {
                progressBar.setVisibility(View.VISIBLE);
                if (idlingResource != null) idlingResource.increment();

            } else {
                progressBar.setVisibility(View.GONE);
                if (idlingResource != null) idlingResource.decrement();
            }
        });
    }

    /**
     * Only called from test, creates and returns a new {@link CountingIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
