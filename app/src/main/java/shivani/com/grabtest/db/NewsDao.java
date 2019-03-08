package shivani.com.grabtest.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 06/03/19.
 */
@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertArticle(Article article);

    @Query("SELECT * FROM article")
    public List<Article> getArticles();

    @Query("DELETE FROM article")
    void deleteAllNews();
}
