package shivani.com.grabtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import shivani.com.grabtest.R;
import shivani.com.grabtest.util.AppConstants;
import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 07/03/19.
 */
public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.wv_news)
    WebView wvNews;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private Article article;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        fetchDataFromIntent();
        initUi();
    }

    private void initUi() {
        progressBar.setVisibility(View.VISIBLE);
        wvNews.setHorizontalScrollBarEnabled(false);
        wvNews.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        wvNews.loadUrl(article.getUrl());

    }

    private void fetchDataFromIntent() {
        Intent intent = getIntent();
        article = intent.getParcelableExtra(AppConstants.NEWS_DATA);
    }
}
