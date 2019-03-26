package shivani.com.grabtest.ui.article;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import shivani.com.grabtest.repository.NewsDataSource;
import shivani.com.grabtest.repository.NewsDataSourceFactory;
import shivani.com.grabtest.repository.NewsRepository;
import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 05/03/19.
 */
public class NewsViewModel extends ViewModel {

    private NewsDataSourceFactory factory;
    private CompositeDisposable disposables;
    private LiveData<Boolean> isUpdating = new MutableLiveData<>();

    private LiveData<PagedList<Article>> articlesLiveData = new MutableLiveData<>();

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        disposables = new CompositeDisposable();
        factory = new NewsDataSourceFactory(newsRepository, disposables);
        initializePaging();
    }

    private void initializePaging() {
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(20)
                        .setPrefetchDistance(10)
                        .setPageSize(20).build();

        articlesLiveData = new LivePagedListBuilder<>(factory, pagedListConfig).build();
        isUpdating = Transformations.switchMap(factory.getNewsLiveData(), NewsDataSource::getIsLoadingData);
    }

    public LiveData<Boolean> isUpdating() {
        return isUpdating;
    }

    public LiveData<PagedList<Article>> getArticlesLiveData() {
        return articlesLiveData;
    }
}
