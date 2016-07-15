package elghamry.android.com.elghamry_movie_app;

import com.firebase.client.Firebase;

/**
 * Created by ELGHAMRY on 29/04/2016.
 */
public class ElGHAMRY_movie_app extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}