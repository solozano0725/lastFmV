package com.valid.lastfmclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopArtists {
    @SerializedName("artist")
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

}
