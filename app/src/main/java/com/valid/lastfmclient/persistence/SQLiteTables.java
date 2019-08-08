package com.valid.lastfmclient.persistence;

@SuppressWarnings("WeakerAccess")
public class SQLiteTables {



    public static final String TABLE_ARTIST = "table_artist";
    public static final String ARTIST_ID = "artist_id";
    public static final String ARTIST_MBID = "artist_mbid";
    public static final String ARTIST_NAME = "artist_name";
    public static final String ARTIST_IMAGE = "artist_image";
    public static final String ARTIST_STREAMABLE = "artist_streamable";
    public static final String ARTIST_PLAYCOUNT = "artist_playcount";
    public static final String ARTIST_URL = "artist_url";

    public static final String TABLE_TRACK = "table_track";
    public static final String TRACK_ID = "track_id";
    public static final String TRACK_DURATION = "track_duration";
    public static final String TRACK_MBID = "track_mbid";
    public static final String TRACK_NAME = "track_name";
    public static final String TRACK_IMAGE = "track_image";
    public static final String TRACK_PLAYCOUNT = "track_playcount";
    public static final String TRACK_ARTIST = "track_artist";
    public static final String TRACK_URL = "track_url";

    public static final String DB_ARTIST = "CREATE TABLE " + TABLE_ARTIST + "(" + ARTIST_ID + " INTEGER PRIMARY KEY, "
            + ARTIST_MBID + " TEXT, " + ARTIST_NAME + " TEXT, " + ARTIST_IMAGE + " TEXT, " + ARTIST_STREAMABLE + " TEXT, "
            + ARTIST_PLAYCOUNT + " TEXT, " + ARTIST_URL + " TEXT " + ")";

    public static final String DB_TRACK = "CREATE TABLE " + TABLE_TRACK + "(" + TRACK_ID + " INTEGER PRIMARY KEY, "
            + TRACK_DURATION + " TEXT, " + TRACK_MBID + " TEXT, " + TRACK_NAME + " TEXT, " + TRACK_IMAGE + " TEXT, "
            + TRACK_PLAYCOUNT + " TEXT, " + TRACK_ARTIST + " TEXT, " + TRACK_URL+ " TEST "+ ")";

    public static final String UPDATE_TABLES = "DROP TABLE IF EXISTS " + SQLiteTables.DB_ARTIST + ","+SQLiteTables.DB_TRACK+";";
}
