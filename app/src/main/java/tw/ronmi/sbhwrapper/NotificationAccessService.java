package tw.ronmi.sbhwrapper;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

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

    private void handle(StatusBarNotification sbn, String action) {
        final Notification n = sbn.getNotification();

        final Intent i = new Intent(SIGN);
        i.putExtra(ACTION, action);
        i.putExtra(TITLE, n.extras.getCharSequence("android.title"));
        i.putExtra(TEXT, n.extras.getCharSequence("android.text"));
        i.putExtra(CATEGORY, n.category);
        i.putExtra(POSTAT, sbn.getPostTime());

        final PackageManager pm = getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo( this.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String app = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        i.putExtra(APP, app);

        sendBroadcast(i);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        handle(sbn, POSTED);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        handle(sbn, REMOVED);
    }
}
