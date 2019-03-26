package shivani.com.grabtest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shivani.com.grabtest.vo.NewsResponse;

/**
 * Created by Shivani on 05/03/19.
 */
public interface AppRequestService {

    @GET("v2/top-headlines")
    Observable<NewsResponse> getArticle(@Query("country") String country, @Query("apiKey") String apiKey);

}
