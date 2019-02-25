package com.oitsme.widgetdemo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.oitsme.widgetdemo.Device;
import com.oitsme.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2018/1/22.
 */

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final static String TAG = "Widget";
    private static final String TYPE = "Type";
    private Context mContext;
    private int mAppWidgetId;

    private static List<Device> mDevices;

    /**
     * 构造ListRemoteViewsFactory
     */
    ListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        //  HashMap<String, Object> map;

        // 获取 item_widget_device.xml 对应的RemoteViews
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_device);

        // 设置 第position位的“视图”的数据
        Device device = mDevices.get(position);
        //  rv.setImageViewResource(R.id.iv_lock, ((Integer) map.get(IMAGE_ITEM)).intValue());
        rv.setTextViewText(R.id.tv_name, device.getName());

        // 设置 第position位的“视图”对应的响应事件
        Intent fillInIntent = ListViewReceiver.newIntent(0, position);
        rv.setOnClickFillInIntent(R.id.rl_widget_device, fillInIntent);


        Intent lockIntent = ListViewReceiver.newIntent(1, position);
        rv.setOnClickFillInIntent(R.id.iv_lock, lockIntent);

        Intent unlockIntent = ListViewReceiver.newIntent(2, position);
        rv.setOnClickFillInIntent(R.id.iv_unlock, unlockIntent);

        return rv;
    }


    /**
     * 初始化ListView的数据
     */
    private void initListViewData() {
        mDevices = new ArrayList<>();
        mDevices.add(new Device("Hello", 0));
        mDevices.add(new Device("Hey", 1));
    }

    private static int i;

    static void refresh() {
        i++;
        mDevices.add(new Device("Refresh" + i, 1));
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        // 初始化“集合视图”中的数据
        initListViewData();
    }

    @Override
    public int getCount() {
        // 返回“集合视图”中的数据的总数
        return mDevices.size();
    }

    @Override
    public long getItemId(int position) {
        // 返回当前项在“集合视图”中的位置
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        // 只有一类 ListView
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
        mDevices.clear();
    }
}
