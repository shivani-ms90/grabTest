package shivani.com.grabtest.ui.article;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import shivani.com.grabtest.repository.NewsRepository;
import shivani.com.grabtest.vo.NewsResponse;

/**
 * Created by Shivani on 05/03/19.
 */
public class NewsViewModel extends ViewModel {

    private static final String TAG = NewsViewModel.class.getSimpleName();
    private CompositeDisposable disposables;
    private NewsRepository newsRepository;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        disposables = new CompositeDisposable();
    }

    public LiveData<NewsResponse> getNews() {
        final MutableLiveData<NewsResponse> newsResponse = new MutableLiveData<>();
        Observable<NewsResponse> observable = newsRepository.loadNews1();
        if (observable != null) {
            observable.doOnSubscribe(disposable -> {
                isUpdating.postValue(true);
                disposables.add(disposable);
            }).subscribeOn(Schedulers.io()).subscribe(new DisposableObserver<NewsResponse>() {
                @Override
                public void onNext(NewsResponse value) {
                    isUpdating.postValue(false);
                    newsResponse.postValue(value);
                }

                @Override
                public void onError(Throwable e) {
                    isUpdating.postValue(false);
                    error.postValue(e.getLocalizedMessage());
                    Log.d(TAG, "onError() called with: e = [" + e + "]");
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete() called");
                }
            });
        }
        return newsResponse;
    }

    public LiveData<Boolean> isUpdating() {
        return isUpdating;
    }

    public LiveData<String> getError() {
        return error;
    }

}
