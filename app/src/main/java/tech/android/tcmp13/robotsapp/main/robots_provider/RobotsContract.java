package tech.android.tcmp13.robotsapp.main.robots_provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public interface RobotsContract {

    int DB_VERSION = 1;
    String DB_NAME = "Robots.db";

    //Content Provider Contract
    String CONTENT_AUTHORITY = "tech.android.tcmp13.robotsapp";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    String ROBOTS_PATH = RobotEntry.TABLE_NAME;

    interface RobotEntry extends BaseColumns {

        String TABLE_NAME = "robots";
        String COLUMNS_NAME = "name";
        String COLUMNS_BRAND = "brand";
        String COLUMNS_TYPE = "type";

        //Content Provider Constants
        Uri ROBOTS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ROBOTS_PATH);
        String ROBOT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ROBOTS_PATH;
        String ROBOT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ROBOTS_PATH;
    }
}
