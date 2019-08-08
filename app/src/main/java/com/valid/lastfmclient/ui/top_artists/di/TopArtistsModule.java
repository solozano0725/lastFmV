package com.valid.lastfmclient.ui.top_artists.di;

import android.content.Context;
import com.valid.lastfmclient.BuildConfig;
import com.valid.lastfmclient.contract.TopArtistsContract;
import com.valid.lastfmclient.ui.top_artists.TopArtistsInteractor;
import com.valid.lastfmclient.ui.top_artists.TopArtistsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class TopArtistsModule {
    TopArtistsContract.TopArtistsView mView;

    private Context context;

    public TopArtistsModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    public TopArtistsModule(TopArtistsContract.TopArtistsView view) {
        mView = view;
    }

    // provides the view to create the top artists presenter
    @Singleton
    @Provides
    public TopArtistsContract.TopArtistsView providesTopArtistsView() {
        return this.mView;
    }

    // provides a converter factory to create the retrofit instance
    @Singleton
    @Provides
    public Converter.Factory providesConverterFactory() {
        return GsonConverterFactory.create();
    }

    // provides a call adapter factory needed to integrate rxjava with retrofit
    @Singleton
    @Provides
    public CallAdapter.Factory providesCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    // provides a retrofit instance to create the top artists interactor
    @Singleton
    @Provides
    public Retrofit providesRetrofit(Converter.Factory converter, CallAdapter.Factory adapter) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(adapter)
                .addConverterFactory(converter)
                .build();
    }

    // provides top artists interactor to make an instance of the presenter
    @Singleton
    @Provides
    public TopArtistsInteractor providesTopArtistsInteractor(Context c) {
        return new TopArtistsInteractor(c);
    }

    // provides top artists presenter
    @Singleton
    @Provides
    public TopArtistsPresenter providesTopArtistsPresenter(TopArtistsContract.TopArtistsView view, TopArtistsInteractor interactor) {
        return new TopArtistsPresenter(view, interactor);

    }

}

