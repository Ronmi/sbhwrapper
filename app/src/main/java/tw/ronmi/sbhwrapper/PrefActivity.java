package tw.ronmi.sbhwrapper;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
    }

    public void openNotifyPermPage(View v) {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    private void send(String app, String action, String title, String text, String category, long post_at) {
        if (category != Notification.CATEGORY_MESSAGE) {
            return;
        }
    }

    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent i) {
            String action, title, text, app, category;
            long post_at;

            action = i.getStringExtra(NotificationAccessService.ACTION);
            title = i.getStringExtra(NotificationAccessService.TITLE);
            text = i.getStringExtra(NotificationAccessService.TEXT);
            app = i.getStringExtra(NotificationAccessService.APP);
            category = i.getStringExtra(NotificationAccessService.CATEGORY);
            post_at = i.getLongExtra(NotificationAccessService.POSTAT, System.currentTimeMillis());

            send(app, action, title, text, category, post_at);
        }
    }
}
