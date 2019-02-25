package com.oitsme.widgetdemo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author Administrator
 */
public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this, intent);
    }
}
