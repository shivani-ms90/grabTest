package shivani.com.grabtest.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import javax.inject.Inject;

import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 06/03/19.
 */
@TypeConverters({Converters.class})
@Database(entities = Article.class, version = 1)
public abstract class NewsDb extends RoomDatabase {

    public abstract NewsDao newsDao();

}
