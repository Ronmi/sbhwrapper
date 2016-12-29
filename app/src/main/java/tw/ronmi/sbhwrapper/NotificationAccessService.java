package tw.ronmi.sbhwrapper;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.notification.NotificationUtil;

/**
 * Created by ronmi on 2016/12/29.
 */

public class NotificationAccessService extends NotificationListenerService {

    public static final String POSTED = "posted";
    public static final String REMOVED = "removed";
    public static final String SIGN = "tw.ronmi.sbhwrapper.NOTIFICATION";

    public static final String ACTION = "action";
    public static final String APP = "app";
    public static final String CATEGORY = "category";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String POSTAT = "post_at";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.LOG_TAG, "NLS started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.LOG_TAG, "NLS destroyed");
    }

    private void send(String app, String action, String title, String text, String category, long post_at) {
        if (!Notification.CATEGORY_MESSAGE.equals(category)) {
            Log.d(Constants.LOG_TAG, "Category " + category + " is not message");
            return;
        }

        long id = NotificationUtil.getSourceId(getApplicationContext(), Constants.EXTENSION_SPECIFIC_ID);
        if (id == NotificationUtil.INVALID_ID) {
            Log.e(Constants.LOG_TAG, "Failed to insert data");
            return;
        }

        ContentValues v = new ContentValues();
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.EVENT_READ_STATUS, action == NotificationAccessService.REMOVED);
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.DISPLAY_NAME, title);
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.MESSAGE, text);
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.PERSONAL, 1);
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.PUBLISHED_TIME, post_at);
        v.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.SOURCE_ID, id);
        NotificationUtil.addEvent(getApplicationContext(), v);

        Log.d(Constants.LOG_TAG, "Notification sent: " + title + " / " + text);
    }

    private void handle(StatusBarNotification sbn, String action) {
        final Notification n = sbn.getNotification();

        final PackageManager pm = getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(sbn.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String app = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        CharSequence title = n.extras.getCharSequence("android.title");
        CharSequence text = n.extras.getCharSequence("android.text");
        send(
                app,
                action,
                title == null ? null : title.toString(),
                text == null ? null : text.toString(),
                n.category,
                sbn.getPostTime()
        );
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        handle(sbn, POSTED);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        handle(sbn, REMOVED);
    }
}
