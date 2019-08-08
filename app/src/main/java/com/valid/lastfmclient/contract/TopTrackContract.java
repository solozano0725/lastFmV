package com.valid.lastfmclient.contract;

import com.valid.lastfmclient.model.TopTracksResponse;
import com.valid.lastfmclient.model.Track;

import java.util.List;

import io.reactivex.Single;

public interface TopTrackContract {

    interface TopTracksInteractor {
        Single<TopTracksResponse> getTopTracks(int limit, String apiKey);

    }

    interface TopTracksPresenter  {
        void onDestroy();
        void getTopTracks(int limit,String apiKey);

    }

    interface TopTracksView {
        void showProgress();

        void hideProgress();

        void showError();

        void updateData(List<Track> tracks);
        void showEmpty();
        void hidEmpty();
    }
}
