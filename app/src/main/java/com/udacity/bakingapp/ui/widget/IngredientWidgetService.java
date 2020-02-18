package com.udacity.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.local.IngredientEntity;
import com.udacity.bakingapp.data.repository.RecipeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IngredientWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetItemFactory(this.getApplicationContext(), intent);
    }

    public class IngredientWidgetItemFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private List<IngredientEntity> ingredients;

        private CompositeDisposable disposable = new CompositeDisposable();

        public IngredientWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            disposable.add(
                    RecipeRepository.getFavoriteIngredients()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::updateUIData)
            );
        }

        @Override
        public void onDataSetChanged() {
            disposable.add(
                    RecipeRepository.getFavoriteIngredients()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::updateUIData)
            );
        }

        @Override
        public void onDestroy() {
            disposable.dispose();
        }

        @Override
        public int getCount() {
            if (ingredients == null || ingredients.isEmpty()) return 0;
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            IngredientEntity entity = ingredients.get(position);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);

            views.setTextViewText(R.id.tv_ingredient_widget, entity.ingredient);
            views.setTextViewText(R.id.tv_measure_widget, entity.measure);
            views.setTextViewText(R.id.tv_quantity_widget, String.valueOf(entity.quantity));

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private void updateUIData(List<IngredientEntity> entities) {
            ingredients = entities;
        }
    }
}
