package com.valid.lastfmclient.ui.top_tracks;

import androidx.annotation.NonNull;

import com.valid.lastfmclient.contract.TopTrackContract;
import com.valid.lastfmclient.model.TopTracksResponse;
import com.valid.lastfmclient.model.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class TopTracksPresenter implements TopTrackContract.TopTracksPresenter {
    Disposable mDisposable;
    TopTracksInteractor mInteractor;
    TopTrackContract.TopTracksView mView;

    public TopTracksPresenter(TopTrackContract.TopTracksView view, TopTracksInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void getTopTracks(int limit, String apiKey) {
        disposeRequest();
        mView.showProgress();
        mView.hidEmpty();
        mDisposable = mInteractor.getTopTracks(limit, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(topTracksResponse -> {
                    if (topTracksResponse != null && topTracksResponse.getTopTracks() != null && topTracksResponse.getTopTracks().getTracks() != null) {
                        return topTracksResponse.getTopTracks().getTracks();
                    }
                    return new ArrayList<Track>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tracks -> {
                    mView.hideProgress();
                    if (tracks.size() == 0) {
                        mView.showEmpty();
                    }
                    mView.updateData(tracks);
                }, throwable -> {
                    mView.hideProgress();
                    mView.showError();

                });
    }

    @Override
    public void onDestroy() {
        disposeRequest();

    }

    private void disposeRequest() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
