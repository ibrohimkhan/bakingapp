package com.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.bakingapp.ui.widget.IngredientWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static void updateWidgetUI(Context context, AppWidgetManager manager, int[] ids) {
        // Instruct the widget manager to update the widget
        for (int id : ids) {
            Intent serviceIntent = new Intent(context, IngredientWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
            views.setRemoteAdapter(R.id.sv_ingredients, serviceIntent);
            views.setEmptyView(R.id.sv_ingredients, R.id.tv_ingredients_empty);

            manager.updateAppWidget(id, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int id : appWidgetIds) {
            Intent serviceIntent = new Intent(context, IngredientWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
            views.setRemoteAdapter(R.id.sv_ingredients, serviceIntent);
            views.setEmptyView(R.id.sv_ingredients, R.id.tv_ingredients_empty);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(id, views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

