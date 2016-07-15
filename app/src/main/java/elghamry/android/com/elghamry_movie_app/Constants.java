package elghamry.android.com.elghamry_movie_app;

/**
 * Created by ELGHAMRY on 24/04/2016.
 */
public class Constants {
    /**
     * Constants for Firebase URL
     */

    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    /**
     * Constants for Firebase login
     */
    public static final String PASSWORD_PROVIDER = "password";


    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";

    /**
     * Constants for Firebase object properties
     */

    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where active lists are stored (ie "activeLists")
     */

    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_FAV = "favourite";
    public static final String FIREBASE_LOCATION_WL= "watchList";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    public static final String FIREBASE_URL_FAV = FIREBASE_URL + "/" + FIREBASE_LOCATION_FAV;
    public static final String FIREBASE_URL_WL = FIREBASE_URL + "/" + FIREBASE_LOCATION_WL;

}
