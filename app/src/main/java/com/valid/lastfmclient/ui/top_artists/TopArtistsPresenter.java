package com.valid.lastfmclient.ui.top_artists;

import com.valid.lastfmclient.contract.TopArtistsContract;
import com.valid.lastfmclient.model.Artist;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;


public class TopArtistsPresenter implements TopArtistsContract.TopArtistsPresenter {
    Disposable mDisposable;
    TopArtistsContract.TopArtistsView mView;
    TopArtistsInteractor mInteractor;

    public TopArtistsPresenter(TopArtistsContract.TopArtistsView view, TopArtistsInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override

    public void onDestroy() {
        disposeRequest();
    }

    @Override
    public void getUserTopArtists(int limit, String apiKey) {
        disposeRequest();
        mView.showProgress();
        mView.hidEmpty();
        mDisposable = mInteractor.getTopArtists(limit, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(topArtistsResponse -> {
                    if (topArtistsResponse != null && topArtistsResponse.getTopartists() != null) {
                        return topArtistsResponse.getTopartists().getArtists();
                    }
                    return new ArrayList<Artist>();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artists -> {
                    mView.hideProgress();
                    if (artists.size() == 0) {
                        mView.showEmpty();
                    }
                    mView.updateData(artists);

                }, throwable -> mView.showError());

    }

    private void disposeRequest() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
