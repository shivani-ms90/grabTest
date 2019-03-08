package shivani.com.grabtest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import shivani.com.grabtest.api.AppRequestService;
import shivani.com.grabtest.db.NewsDb;
import shivani.com.grabtest.util.AppUtil;
import shivani.com.grabtest.vo.Article;
import shivani.com.grabtest.vo.NewsResponse;

/**
 * Created by Shivani on 05/03/19.
 */
public class NewsRepository {

    private AppRequestService requestService;
    private NewsDb db;
    private AppUtil util;

    @Inject
    NewsRepository(AppRequestService requestService, NewsDb db, AppUtil util) {
        this.requestService = requestService;
        this.db = db;
        this.util = util;
    }

    //approach 1 : if the user is online always fetch fresh data, if offline then show the last updated data
    public Observable<NewsResponse> loadNews1() {
        if (util.isNetworkAvailable()) {
            return requestService.getUser("us", "3466522f76f94a9e92cffdc0d59fe43a").doOnNext(newsResponse -> {
                db.newsDao().deleteAllNews();
                saveResponseInDb(newsResponse);
            });
        } else {
            return Observable.defer(() -> {
                NewsResponse newsResponse = new NewsResponse();
                newsResponse.setArticles((ArrayList<Article>) db.newsDao().getArticles());
                return Observable.just(newsResponse);
            });
        }
    }

    //approach 2nd : show the cached response when its available otherwise fetch from server
    public Observable<NewsResponse> loadNews2() {
        return Observable.defer(() -> {
            List<Article> articles = db.newsDao().getArticles();
            if (articles != null && articles.size() > 0) {
                NewsResponse newsResponse = new NewsResponse();
                newsResponse.setArticles((ArrayList<Article>) articles);
                return Observable.just(newsResponse);
            } else {
                return requestService.getUser("us", "3466522f76f94a9e92cffdc0d59fe43a").doOnNext(newsResponse -> {
                    db.newsDao().deleteAllNews();
                    saveResponseInDb(newsResponse);
                });
            }
        });
    }


    private void saveResponseInDb(NewsResponse newsResponse) {
        Observable.fromIterable(newsResponse.getArticles()).observeOn(Schedulers.io()).subscribe(new Observer<Article>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Article article) {
                db.newsDao().insertArticle(article);
            }

            @Override
            public void onError(Throwable e) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        });
    }
}
