package com.valid.lastfmclient.contract;

import android.content.Context;

import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.model.TopArtistsResponse;

import java.util.List;

import io.reactivex.Single;

public interface TopArtistsContract {

    interface TopArtistsInteractor {
        Single<TopArtistsResponse> getTopArtists(int limit, String apiKey);
    }

    interface TopArtistsPresenter {
        void onDestroy();

        void getUserTopArtists(int limit, String apiKey);
    }

    interface TopArtistsView {
        void showProgress();

        void hideProgress();

        void updateData(List<Artist> topArtists);

        void showError();
        void showEmpty();
        void hidEmpty();

    }

}
