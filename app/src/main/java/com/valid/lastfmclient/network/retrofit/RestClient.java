package com.valid.lastfmclient.network.retrofit;

import com.valid.lastfmclient.model.TopArtistsResponse;
import com.valid.lastfmclient.model.TopTracksResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestClient {

    @GET("?method=geo.gettopartists&country=spain&format=json")
    Single<TopArtistsResponse> getTopArtists(@Query("limit") int limit, @Query("api_key") String apiKey);

    @GET("?method=geo.gettoptracks&country=spain&format=json")
    Single<TopTracksResponse> getTopTracks(@Query("limit") int limit, @Query("api_key") String apiKey);

}