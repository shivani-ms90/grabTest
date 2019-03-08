package shivani.com.grabtest.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import shivani.com.grabtest.R;
import shivani.com.grabtest.adapter.NewsListAdapter;
import shivani.com.grabtest.di.Injectable;
import shivani.com.grabtest.ui.ViewModelFactory;
import shivani.com.grabtest.ui.article.NewsViewModel;
import shivani.com.grabtest.util.AppConstants;

public class NewsListActivity extends AppCompatActivity implements Injectable {

    @Inject
    ViewModelFactory viewModelFactory;
    NewsViewModel newsViewModel;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private NewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        newsViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel.class);
        initUi();
        initObservations();
    }

    private void initUi() {
        mAdapter = new NewsListAdapter();
        mAdapter.setOnItemClickListener(article -> {
            Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);
            intent.putExtra(AppConstants.NEWS_DATA, article);
            startActivity(intent);
        });
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initObservations() {
        newsViewModel.getNews().observe(this, newsResponse -> {
            if (newsResponse != null) {
                mAdapter.refreshList(newsResponse.getArticles());
            }
        });
        newsViewModel.isUpdating().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        newsViewModel.getError().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_LONG).show());
    }
}
