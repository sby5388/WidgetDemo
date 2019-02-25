package com.oitsme.widgetdemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.oitsme.widgetdemo.R;

/**
 * @author Administrator
 */
public class ListWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WIDGET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Log.d(TAG, "ListWidgetProvider onUpdate");
        for (int appWidgetId : appWidgetIds) {
            // 获取AppWidget对应的视图
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

            // 设置响应 “按钮(bt_refresh)” 的intent
            Intent btIntent = ListViewReceiver.newIntent(context);
            PendingIntent btPendingIntent = PendingIntent.getBroadcast(context, 0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.tv_refresh, btPendingIntent);

            // 设置 “ListView” 的adapter。
            // (01) intent: 对应启动 ListWidgetService(RemoteViewsService) 的intent
            // (02) setRemoteAdapter: 设置 ListView的适配器
            //    通过setRemoteAdapter将ListView和ListWidgetService关联起来，
            //    以达到通过 ListWidgetService 更新 ListView 的目的
            Intent serviceIntent = new Intent(context, ListWidgetService.class);
            remoteViews.setRemoteAdapter(R.id.lv_device, serviceIntent);


            // 设置响应 “ListView” 的intent模板
            // 说明：“集合控件(如GridView、ListView、StackView等)”中包含很多子元素。
            //     它们不能像普通的按钮一样通过 setOnClickPendingIntent 设置点击事件，必须先通过两步。
            //        (01) 通过 setPendingIntentTemplate 设置 “intent模板”，这是比不可少的！
            //        (02) 然后在处理该“集合控件”的RemoteViewsFactory类的getViewAt()接口中 通过 setOnClickFillInIntent 设置“集合控件的某一项的数据”
            Intent listIntent = ListViewReceiver.newIntent(context, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, listIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 设置intent模板
            remoteViews.setPendingIntentTemplate(R.id.lv_device, pendingIntent);
            // 调用集合管理器对集合进行更新
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
