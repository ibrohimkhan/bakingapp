package com.udacity.bakingapp.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.Event;
import com.udacity.bakingapp.data.common.SharedViewModel;
import com.udacity.bakingapp.data.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";

    private SharedViewModel sharedViewModel;
    private RecipeViewModel viewModel;
    private Recipe recipe;

    @BindView(R.id.tb_recipe)
    Toolbar toolbar;

    @BindView(R.id.tv_recipe_title)
    TextView tvTitle;

    @BindView(R.id.rv_steps)
    RecyclerView recyclerView;

    private StepAdapter adapter;

    public static RecipeFragment newInstance() {
        Bundle args = new Bundle();

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
            viewModel = new ViewModelProvider(getActivity()).get(RecipeViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);

        toolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count_recipe)));

        sharedViewModel.getSelectedRecipe().observe(this, event -> {
            if (event == null) return;
            recipe = event.peek();

            tvTitle.setText(recipe.name);

            adapter = new StepAdapter(viewModel, recipe.steps, getString(R.string.recipe_ingredients));
            recyclerView.setAdapter(adapter);
        });

        viewModel.step.observe(this, step -> {
            sharedViewModel.selectedStep(step);
        });

        viewModel.ingredient.observe(this, event -> {
            if (recipe != null && event.getIfNotHandled() != null) {
                sharedViewModel.selectedIngredients(new Event<>(recipe.ingredients));
            }
        });
    }
}
