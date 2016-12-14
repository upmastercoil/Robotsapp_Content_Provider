package tech.android.tcmp13.robotsapp.main.robots_provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract;

/**
 * Created by tcmp13-t on 12/4/2016.
 * <p>
 * The first class to interact with the DB directly, must be implemented by android
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(Context context) {

        super(context, RobotsContract.DB_NAME, null, RobotsContract.DB_VERSION);
    }

    /**
     * Called only if the .db file doesn't exist.
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createSql = "CREATE TABLE " + RobotsContract.RobotEntry.TABLE_NAME + " (" + RobotsContract.RobotEntry.COLUMNS_NAME + " TEXT," +
                RobotsContract.RobotEntry.COLUMNS_BRAND + " TEXT, " + RobotsContract.RobotEntry.COLUMNS_TYPE + " INTEGER," +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        sqLiteDatabase.execSQL(createSql);
    }

    /**
     * Called when the DB_VERSION constant value is changed;
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String deleteSql = "DROP TABLE IF EXIST " + RobotsContract.RobotEntry.COLUMNS_NAME + ";";
        sqLiteDatabase.execSQL(deleteSql);
        onCreate(sqLiteDatabase);
    }
}
