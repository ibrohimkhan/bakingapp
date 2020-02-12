package com.udacity.bakingapp.ui.recipelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.SharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    public static final String TAG = "RecipeListFragment";

    private SharedViewModel sharedViewModel;
    private RecipeListViewModel viewModel;

    private RecipeListAdapter adapter;

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
    }
}
