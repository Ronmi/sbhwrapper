package tw.ronmi.sbhwrapper;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.notification.Notification;
import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronmi on 2016/12/29.
 */

public class ExtInformation extends RegistrationInformation {

    private Context context;

    public ExtInformation(final Context context) {
        this.context = context;
    }

    @Override
    public int getRequiredNotificationApiVersion() {
        return 1;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredControlApiVersion() {
        return API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredSensorApiVersion() {
        return API_NOT_REQUIRED;
    }

    private String getString(final int id) {
        return context.getString(id);
    }

    private String getUriString(final int id) {
        return ExtensionUtils.getUriString(context, id);
    }

    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        String icon = getUriString(R.mipmap.ic_launcher);
        String text = getString(R.string.ext_conf_text);
        String name = getString(R.string.app_name);

        ContentValues ret = new ContentValues();
        ret.put(Registration.ExtensionColumns.CONFIGURATION_ACTIVITY, PrefActivity.class.getName());
        ret.put(Registration.ExtensionColumns.CONFIGURATION_TEXT, text);
        ret.put(Registration.ExtensionColumns.EXTENSION_ICON_URI, icon);
        ret.put(Registration.ExtensionColumns.EXTENSION_48PX_ICON_URI, icon);
        ret.put(Registration.ExtensionColumns.EXTENSION_KEY, Constants.EXTENSION_KEY);
        ret.put(Registration.ExtensionColumns.HOST_APP_ICON_URI, icon);
        ret.put(Registration.ExtensionColumns.NAME, name);
        ret.put(Registration.ExtensionColumns.NOTIFICATION_API_VERSION,
                getRequiredNotificationApiVersion());
        ret.put(Registration.ExtensionColumns.PACKAGE_NAME, context.getPackageName());
        return ret;
    }

    private ContentValues getSourceConfiguration(final String extensionId) {
        String icon = getUriString(R.mipmap.ic_launcher);
        String textToSpeech = getString(R.string.tts_prefix);

        ContentValues values = new ContentValues();
        values.put(Notification.SourceColumns.ENABLED, true);
        values.put(Notification.SourceColumns.ICON_URI_1, icon);
        values.put(Notification.SourceColumns.ICON_URI_2, icon);
        values.put(Notification.SourceColumns.UPDATE_TIME, System.currentTimeMillis());
        values.put(Notification.SourceColumns.NAME, getString(R.string.app_name));
        values.put(Notification.SourceColumns.EXTENSION_SPECIFIC_ID, extensionId);
        values.put(Notification.SourceColumns.PACKAGE_NAME, context.getPackageName());
        values.put(Notification.SourceColumns.TEXT_TO_SPEECH, textToSpeech);
        return values;
    }

    @Override
    public ContentValues[] getSourceRegistrationConfigurations() {
        // This sample only adds one source but it is possible to add more
        // sources if needed.
        List<ContentValues> list = new ArrayList<ContentValues>();
        list.add(getSourceConfiguration(Constants.EXTENSION_SPECIFIC_ID));
        return list.toArray(new ContentValues[list.size()]);
    }
}
