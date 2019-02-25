package com.oitsme.widgetdemo.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.oitsme.widgetdemo.R;

/**
 * 更新UI
 *
 * @author Administrator
 */
public class ListViewReceiver extends BroadcastReceiver {
    private static final String ACTION_REFRESH_WIDGET = "ACTION_REFRESH_WIDGET";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (ACTION_REFRESH_WIDGET.equals(action)) {
            refreshView(context);
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ListViewReceiver.class);
        intent.setAction(ACTION_REFRESH_WIDGET);
        return intent;
    }

    private void refreshView(final Context context) {
        // 接受“bt_refresh”的点击事件的广播
        Toast.makeText(context, "刷新...", Toast.LENGTH_SHORT).show();
        final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        final ComponentName cn = new ComponentName(context, ListWidgetProvider.class);
        ListRemoteViewsFactory.refresh();
        mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lv_device);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                hideLoading(context);
            }
        }, 1000);
        showLoading(context);
    }
    /**
     * 显示加载loading
     */
    private void showLoading(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        remoteViews.setViewVisibility(R.id.tv_refresh, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.progress_bar, View.VISIBLE);
        remoteViews.setTextViewText(R.id.tv_refresh, "正在刷新...");
        refreshWidget(context, remoteViews, true);
    }

    /**
     * 刷新Widget
     */
    private void refreshWidget(Context context, RemoteViews remoteViews, boolean refreshList) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, ListWidgetProvider.class);
        appWidgetManager.updateAppWidget(componentName, remoteViews);
        if (refreshList) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.lv_device);
        }
    }


    /**
     * 隐藏加载loading
     */
    private void hideLoading(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        remoteViews.setViewVisibility(R.id.progress_bar, View.GONE);
        remoteViews.setTextViewText(R.id.tv_refresh, "刷新");
        refreshWidget(context, remoteViews, false);
    }
}
