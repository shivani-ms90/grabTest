package shivani.com.grabtest.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import shivani.com.grabtest.api.AppRequestService;
import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 08/03/19.
 */
public class NewsDataSourceFactory extends DataSource.Factory<Integer, Article> {

    private MutableLiveData<NewsDataSource> newsLiveData = new MutableLiveData<>();
    private final NewsRepository newsRepository;
    private final CompositeDisposable compositeDisposable;

    public NewsDataSourceFactory(NewsRepository newsRepository, CompositeDisposable compositeDisposable) {
        this.newsRepository = newsRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, Article> create() {
        NewsDataSource dataSource = new NewsDataSource(newsRepository, compositeDisposable);
        newsLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<NewsDataSource> getNewsLiveData() {
        return newsLiveData;
    }
}
