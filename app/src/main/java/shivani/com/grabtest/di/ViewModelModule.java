package shivani.com.grabtest.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import shivani.com.grabtest.ui.ViewModelFactory;
import shivani.com.grabtest.ui.article.NewsViewModel;

/**
 * Created by Shivani on 05/03/19.
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindsUserViewModel(NewsViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);
}

