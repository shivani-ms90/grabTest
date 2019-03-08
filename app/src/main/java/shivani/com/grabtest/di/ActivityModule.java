package shivani.com.grabtest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import shivani.com.grabtest.activity.NewsListActivity;

/**
 * Created by Shivani on 05/03/19.
 */
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract NewsListActivity contributeMainActivity();
}
