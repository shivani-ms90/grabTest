package shivani.com.grabtest.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import okhttp3.OkHttpClient;
import shivani.com.grabtest.GrabApplication;
import shivani.com.grabtest.util.AppUtil;

/**
 * Created by Shivani on 05/03/19.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, RequestServiceModule.class, ActivityModule.class, ViewModelModule.class, AppDbModule.class})
public interface AppComponent {
    void inject(GrabApplication grabApplication);

    OkHttpClient getOkhttpClient();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context application);

        @BindsInstance
        Builder appUtil(AppUtil appUtil);

        AppComponent build();
    }
}
