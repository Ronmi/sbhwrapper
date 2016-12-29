package tw.ronmi.sbhwrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ronmi on 2016/12/29.
 */

public class ExtReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context, ExtService.class);
        context.startService(intent);
    }
}
