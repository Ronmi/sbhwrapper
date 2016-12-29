package tw.ronmi.sbhwrapper;

/**
 * Created by ronmi on 2016/12/29.
 */

public final class Constants {

    /** The extension key. */
    public static final String EXTENSION_KEY =
            Constants.class.getPackage().getName() + ".key";

    /** The extension specific id for the source. */
    public static final String EXTENSION_SPECIFIC_ID =
            "TW_RONMI_SBHWRAPPER";

    /** The log tag. */
    public static final String LOG_TAG = "SBHWrapper";

    /** Hides the default constructor. */
    private Constants() {
    }
}
