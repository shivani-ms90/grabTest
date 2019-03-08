package shivani.com.grabtest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import shivani.com.grabtest.GrabApplication;

/**
 * Created by Shivani on 07/03/19.
 */
public class AppUtil {

    private Context applicationContext;

    @Inject
    public AppUtil(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) applicationContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

}
