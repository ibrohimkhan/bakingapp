package com.udacity.bakingapp.ui.ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.SharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    public static final String TAG = "IngredientFragment";
    private SharedViewModel sharedViewModel;

    @BindView(R.id.tb_ingredients)
    @Nullable
    Toolbar toolbar;

    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerView;

    private IngredientAdapter adapter;

    public static IngredientFragment newInstance() {
        Bundle args = new Bundle();

        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null)
            sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count_ingredient)));

        sharedViewModel.getSelectedIngredients().observe(this, ingredients -> {
            if (ingredients != null && ingredients.peek() != null) {
                adapter = new IngredientAdapter(ingredients.peek());
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
