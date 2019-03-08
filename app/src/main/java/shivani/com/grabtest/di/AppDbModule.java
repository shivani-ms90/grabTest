package shivani.com.grabtest.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import shivani.com.grabtest.GrabApplication;
import shivani.com.grabtest.db.NewsDb;

/**
 * Created by Shivani on 07/03/19.
 */
@Module()
public class AppDbModule {

    @Provides
    @Singleton
    NewsDb provideNewsDb(Context application){
        return Room.databaseBuilder(application.getApplicationContext(), NewsDb.class, "news_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
