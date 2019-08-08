package com.valid.lastfmclient.persistence;

import android.content.ContentValues;
import android.content.Context;
import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.model.Track;

public class SQLiteController {
    private SQLiteHelper sqLiteHelper;
    Context context;

    public SQLiteController(Context mContext){
        this.context = mContext;
        sqLiteHelper = new SQLiteHelper(context);
    }

    public boolean saveArtist(Artist artist){

        int num = (int) (Math.random() * 1000000) + 1;

        ContentValues values = new ContentValues();
        values.put(SQLiteTables.ARTIST_ID, num);
        values.put(SQLiteTables.ARTIST_MBID, artist.getMbid());
        values.put(SQLiteTables.ARTIST_NAME, artist.getName());
        values.put(SQLiteTables.ARTIST_PLAYCOUNT, artist.getPlaycount());
        values.put(SQLiteTables.ARTIST_URL, artist.getUrl());
        values.put(SQLiteTables.ARTIST_IMAGE, artist.getImageUrl());
        values.put(SQLiteTables.ARTIST_STREAMABLE, artist.getStreamable());
        return sqLiteHelper.insertValues(SQLiteTables.TABLE_ARTIST, values);
    }

    public boolean saveTrack(Track track){

        int num = (int) (Math.random() * 1000000) + 1;

        ContentValues values = new ContentValues();
        values.put(SQLiteTables.TRACK_ID, num);
        values.put(SQLiteTables.TRACK_MBID, track.getMbid());
        values.put(SQLiteTables.TRACK_NAME, track.getName());
        values.put(SQLiteTables.TRACK_PLAYCOUNT, track.getPlaycount());
        values.put(SQLiteTables.TRACK_URL, track.getUrl());
        values.put(SQLiteTables.TRACK_IMAGE, track.getImageUrl());
        values.put(SQLiteTables.TRACK_DURATION, track.getDuration());
        values.put(SQLiteTables.TRACK_ARTIST, String.valueOf(track.getArtist()));
        return sqLiteHelper.insertValues(SQLiteTables.TABLE_TRACK, values);
    }

    public Artist getArtistDetail(String name){
        return sqLiteHelper.getArtistDetail(name);
    }
    public Track getTrackDetail(String name){
        return sqLiteHelper.getTrackDetail(name);
    }
}
