package shivani.com.grabtest.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import shivani.com.grabtest.util.AppConstants;
import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 08/03/19.
 */
public class NewsDataSource extends PageKeyedDataSource<Integer, Article> {

    private MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>();
    private NewsRepository newsRepository;
    private CompositeDisposable compositeDisposable;
    private static final int INITIAL_COUNTRY_INDEX = 0;

    public NewsDataSource(NewsRepository newsRepository, CompositeDisposable compositeDisposable) {
        this.newsRepository = newsRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        newsRepository.loadNews1(INITIAL_COUNTRY_INDEX)
                .doOnSubscribe(disposable -> {
                    isLoadingData.postValue(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(newsResponse -> {
                    isLoadingData.postValue(false);
                    callback.onResult(newsResponse.getArticles(), null, INITIAL_COUNTRY_INDEX + 1);
                }, throwable ->
                        isLoadingData.postValue(false));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
//        newsRepository.loadNews1(params.key)
//                .doOnSubscribe(disposable -> {
//                    isLoadingData.postValue(true);
//                    compositeDisposable.add(disposable);
//                })
//                .subscribe(newsResponse -> {
//                    callback.onResult(newsResponse.getArticles(), params.key == 0 ? null : params.key - 1);
//                }, throwable ->
//                        isLoadingData.postValue(false));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        newsRepository.loadNews1(params.key)
                .doOnSubscribe(disposable -> {
                    isLoadingData.postValue(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(newsResponse -> {
                    isLoadingData.postValue(false);
                    callback.onResult(newsResponse.getArticles(), params.key == AppConstants.COUNTRIES.length - 1 ? null : params.key + 1);
                }, throwable ->
                        isLoadingData.postValue(false));
    }

    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }
}
