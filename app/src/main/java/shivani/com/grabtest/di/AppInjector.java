package shivani.com.grabtest.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import dagger.android.AndroidInjection;
import shivani.com.grabtest.GrabApplication;
import shivani.com.grabtest.util.AppUtil;

/**
 * Created by Shivani on 05/03/19.
 * Helper class to automatically inject activity/fragments if they implement {@link Injectable}.
 */
public class AppInjector {

    private AppInjector() {
    }

    public static void init(GrabApplication grabApplication) {
        DaggerAppComponent.builder().context(grabApplication)
                .appUtil(new AppUtil(grabApplication))
                .build()
                .inject(grabApplication);

        grabApplication
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        if (activity instanceof Injectable) {
                            AndroidInjection.inject(activity);
                        }
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }
}
