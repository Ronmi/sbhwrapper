package tw.ronmi.sbhwrapper;

import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * Created by ronmi on 2016/12/29.
 */

public class ExtService extends ExtensionService {

    public ExtService() {
        super(Constants.EXTENSION_KEY);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.LOG_TAG, "Extesion service created.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.LOG_TAG, "Extesion service destroyed.");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new ExtInformation(this);
    }

    @Override
    protected boolean keepRunningWhenConnected() {
        return false;
    }
}
