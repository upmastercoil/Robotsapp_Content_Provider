package tech.android.tcmp13.robotsapp.main.robots_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import tech.android.tcmp13.robotsapp.main.robots_provider.db.DbOpenHelper;

import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.*;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.*;

/**
 * Created by tcmp13-t on 12/11/2016.
 */
public class RobotsProvider extends ContentProvider {

    private static final int ALL_ROBOTS_ID = 1691;
    private static final int ONE_ROBOT_ID = 1692;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(CONTENT_AUTHORITY, ROBOTS_PATH, ALL_ROBOTS_ID);
        //# at the URI will match any length of integers and * any length of characters
        uriMatcher.addURI(CONTENT_AUTHORITY, ROBOTS_PATH + "/#", ONE_ROBOT_ID);
    }

    private DbOpenHelper dbOpenHelper;

    @Override
    public boolean onCreate() {

        dbOpenHelper = new DbOpenHelper(getContext());
        uriMatcher.match(ROBOTS_CONTENT_URI);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db == null)
            return null;

        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_ROBOTS_ID:
                //Selection (where _id = ?) and selection args(string array for the ?) are separated to prevent SQL Injection attempts.
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
                break;
            case ONE_ROBOT_ID:
                selection = _ID + "=?";
                //Uri is built with integer (id) at the end), to get it use ContentUris class
                String idString = String.valueOf(ContentUris.parseId(uri));
                selectionArgs = new String[]{idString};
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
                break;
            default:
                throw new IllegalArgumentException("Nu Nu Nu?! What is up with your URI? " + uri);
        }

        //If there is a context (ui that is waiting to be updated) set the notifications listener to this function
        if (getContext() != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_ROBOTS_ID:
                return ROBOT_LIST_TYPE;
            case ONE_ROBOT_ID:
                return ROBOT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Nu Nu Nu?! What is up with your URI? " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_ROBOTS_ID:
                return insertNewRobot(uri, contentValues);
            default:
                throw new IllegalArgumentException("Nu Nu Nu?! What is up with your URI? " + uri);
        }
    }

    private Uri insertNewRobot(Uri uri, ContentValues contentValues) {

        //Get The DB
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return null;

        //Perform the insertion
        long id = -1;
        try {
            id = db.insert(TABLE_NAME, null, contentValues);
        } finally {
            //Must be performed
            db.close();
        }

        //After insertion check it was successful
        if (id == -1)
            return null;
        //If there is a context (ui that is waiting to be updated) notify this content resolver
        // that for this uri a change has been made
        if (getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_ROBOTS_ID:
                return deleteRobot(uri, selection, selectionArgs);
            case ONE_ROBOT_ID:
                selection = _ID + "=?";
                //Uri is built with integer (id) at the end), to get it use ContentUris class
                String idString = String.valueOf(ContentUris.parseId(uri));
                selectionArgs = new String[]{idString};
                return deleteRobot(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Nu Nu Nu?! What is up with your URI? " + uri);
        }
    }

    private int deleteRobot(Uri uri, String selection, String[] selectionArgs) {

        //Get The DB
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return 0;

        //Perform the insertion
        int affectedRow = 0;
        try {
            affectedRow = db.delete(TABLE_NAME, selection, selectionArgs);
        } finally {
            //Must be performed
            db.close();
        }

        //If there are any rows affected and there is a context (ui that is waiting to be updated)
        // notify this content resolver
        // that for this uri a change has been made
        if (affectedRow > 0 && getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        //After insertion check it was successful
        return affectedRow;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_ROBOTS_ID:
                return updateRobot(uri, contentValues, selection, selectionArgs);
            case ONE_ROBOT_ID:
                selection = _ID + "=?";
                //Uri is built with integer (id) at the end), to get it use ContentUris class
                String idString = String.valueOf(ContentUris.parseId(uri));
                selectionArgs = new String[]{idString};
                return updateRobot(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Nu Nu Nu?! What is up with your URI? " + uri);
        }
    }

    private int updateRobot(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        //Get The DB
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return 0;

        //Perform the insertion
        int affectedRow = 0;
        try {
            affectedRow = db.update(TABLE_NAME, contentValues, selection, selectionArgs);
        } finally {
            //Must be performed
            db.close();
        }

        //If there are any rows affected and there is a context (ui that is waiting to be updated)
        // notify this content resolver
        // that for this uri a change has been made
        if (affectedRow > 0 && getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        //After insertion check it was successful
        return affectedRow;
    }
}
