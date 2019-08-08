package com.valid.lastfmclient.ui.top_tracks.di;

import android.content.Context;
import com.valid.lastfmclient.BuildConfig;
import com.valid.lastfmclient.contract.TopTrackContract;
import com.valid.lastfmclient.ui.top_tracks.TopTracksInteractor;
import com.valid.lastfmclient.ui.top_tracks.TopTracksPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class TopTracksModule {
    TopTrackContract.TopTracksView mView;

    private Context context;

    public TopTracksModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    public TopTracksModule(TopTrackContract.TopTracksView view) {
        mView = view;
    }

    // provides the view to create the top tracks presenter
    @Singleton
    @Provides
    public TopTrackContract.TopTracksView providesTopTracksView() {
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

    // provides a retrofit instance to create the top tracks interactor
    @Singleton
    @Provides
    public Retrofit providesRetrofit(Converter.Factory converter, CallAdapter.Factory adapter) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(adapter)
                .addConverterFactory(converter)
                .build();
    }

    // provides top tracks interactor to make an instance of the presenter
    @Singleton
    @Provides
    public TopTracksInteractor providesTopTopTracksInteractor(Context c) {
        return new TopTracksInteractor(c);
    }

    // provides top albums presenter
    @Singleton
    @Provides
    public TopTracksPresenter providesTopTracksPresenter(TopTrackContract.TopTracksView view, TopTracksInteractor interactor) {
        return new TopTracksPresenter(view, interactor);

    }
}
