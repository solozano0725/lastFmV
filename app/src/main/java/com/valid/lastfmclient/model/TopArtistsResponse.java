package com.valid.lastfmclient.model;

import com.google.gson.annotations.SerializedName;

public class TopArtistsResponse {
    @SerializedName("topartists")
    private TopArtists topArtists;

    public TopArtists getTopartists() {
        return topArtists;
    }

    public void setTopartists(TopArtists topArtists) {
        this.topArtists = topArtists;
    }
}
