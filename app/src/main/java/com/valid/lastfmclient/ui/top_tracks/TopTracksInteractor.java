package com.valid.lastfmclient.ui.top_tracks;

import android.content.Context;
import com.valid.lastfmclient.contract.TopTrackContract;
import com.valid.lastfmclient.model.TopTracksResponse;
import com.valid.lastfmclient.network.retrofit.RestApiAdapter;

import io.reactivex.Single;

public class TopTracksInteractor implements TopTrackContract.TopTracksInteractor {
    RestApiAdapter restApiAdapter;
    Context con;

    public TopTracksInteractor(Context c) {
        con = c;
        restApiAdapter = new RestApiAdapter(con);
    }

    @Override
    public Single<TopTracksResponse> getTopTracks(int limit, String apiKey) {
        return restApiAdapter.restClient().getTopTracks(limit, apiKey);
    }
}
