package sg.edu.rp.c346.id22020383.nationaldayparadethemesongcompilation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_SINGERS + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_STARS + " INTEGER)";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public long insertSong(String title, String singers, int year, int stars) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        long id = db.insert(TABLE_SONGS, null, values);
        db.close();
        return id;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getFiveStarSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_STARS + " = ?";
        String[] selectionArgs = {"5"};
        Cursor cursor = db.query(TABLE_SONGS, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public Song getSongById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_SONGS, null, selection, selectionArgs, null, null, null);
        Song song = null;
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
            int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
            int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));
            song = new Song(id, title, singers, year, stars);
        }
        cursor.close();
        db.close();
        return song;
    }

    public void updateSong(Song song) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGERS, song.getSingers());
        values.put(COLUMN_YEAR, song.getYear());
        values.put(COLUMN_STARS, song.getStars());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(song.getId())};
        db.update(TABLE_SONGS, values, whereClause, whereArgs);
        db.close();
    }

    public void deleteSong(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_SONGS, whereClause, whereArgs);
        db.close();
    }

    public ArrayList<Integer> getDistinctYears() {
        ArrayList<Integer> years = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_YEAR + " FROM " + TABLE_SONGS, null);
        if (cursor.moveToFirst()) {
            do {
                int year = cursor.getInt(0);
                years.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return years;
    }

    public ArrayList<Song> getSongsByYear(int year) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_YEAR + " = ?";
        String[] selectionArgs = {String.valueOf(year)};
        Cursor cursor = db.query(TABLE_SONGS, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int songYear = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));
                Song song = new Song(id, title, singers, songYear, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
}