package com.valid.lastfmclient.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.valid.lastfmclient.model.Artist;
import com.valid.lastfmclient.model.Track;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_lastfm";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = SQLiteHelper.class.getSimpleName();



    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteTables.DB_ARTIST);
        db.execSQL(SQLiteTables.DB_TRACK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLiteTables.UPDATE_TABLES);
        onCreate(db);
    }

    public boolean insertValues(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1){
            Log.d(TAG, "Error al guardar informacion");
            return false;
        }else{
            Log.d(TAG, "Se guardo la información");
            return true;
        }
    }

    public Artist getArtistDetail(String name){
        Artist artist;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={
                SQLiteTables.ARTIST_ID,
                SQLiteTables.ARTIST_MBID,
                SQLiteTables.ARTIST_NAME,
                SQLiteTables.ARTIST_IMAGE,
                SQLiteTables.ARTIST_PLAYCOUNT,
                SQLiteTables.ARTIST_STREAMABLE,
                SQLiteTables.ARTIST_URL
        };

        String selection = SQLiteTables.ARTIST_NAME + " =? " ;
        String[] args={name};

        Cursor cursor = db.query(SQLiteTables.TABLE_ARTIST, columns,
                selection, args, null, null, null);

        if (cursor != null && cursor.moveToFirst()){

            artist = new Artist();
            artist.setMbid(cursor.getString(1));
            artist.setName(cursor.getString(2));
            artist.setPlaycount(cursor.getString(5));
            artist.setUrl(cursor.getString(6));
            artist.setStreamable(cursor.getString(4));

            return artist;
        }

        return null;
    }

    public Track getTrackDetail(String name){
        Track track;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={
                SQLiteTables.TRACK_ID,
                SQLiteTables.TRACK_MBID,
                SQLiteTables.TRACK_NAME,
                SQLiteTables.TRACK_IMAGE,
                SQLiteTables.TRACK_PLAYCOUNT,
                SQLiteTables.TRACK_ARTIST,
                SQLiteTables.TRACK_URL,
                SQLiteTables.TRACK_DURATION
        };

        String selection = SQLiteTables.TRACK_NAME + " =? " ;
        String[] args={name};

        Cursor cursor = db.query(SQLiteTables.TABLE_TRACK, columns,
                selection, args, null, null, null);

        if (cursor != null && cursor.moveToFirst()){

            track = new Track();
            track.setMbid(cursor.getString(2));
            track.setName(cursor.getString(3));
            track.setPlaycount(cursor.getString(5));
            track.setUrl(cursor.getString(6));
            track.setDuration(cursor.getString(1));

            return track;
        }

        return null;
    }

}
