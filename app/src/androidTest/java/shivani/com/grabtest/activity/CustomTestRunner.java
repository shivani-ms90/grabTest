package shivani.com.grabtest.activity;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import shivani.com.grabtest.GrabApplication;

/**
 * Created by Shivani on 08/03/19.
 */
public class CustomTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, GrabApplication.class.getName(), context);
    }
}
