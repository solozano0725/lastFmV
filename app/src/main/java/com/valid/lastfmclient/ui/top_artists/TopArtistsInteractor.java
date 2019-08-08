package com.valid.lastfmclient.ui.top_artists;

import android.content.Context;

import com.valid.lastfmclient.contract.TopArtistsContract;
import com.valid.lastfmclient.model.TopArtistsResponse;
import com.valid.lastfmclient.network.retrofit.RestApiAdapter;
import io.reactivex.Single;

public class TopArtistsInteractor implements TopArtistsContract.TopArtistsInteractor {

    RestApiAdapter restApiAdapter;
    Context con;
    public TopArtistsInteractor(Context c) {
        con = c;
        restApiAdapter = new RestApiAdapter(con);
    }

    @Override
    public Single<TopArtistsResponse> getTopArtists(int limit, String apiKey) {
        return restApiAdapter.restClient().getTopArtists(limit, apiKey);
    }
}
