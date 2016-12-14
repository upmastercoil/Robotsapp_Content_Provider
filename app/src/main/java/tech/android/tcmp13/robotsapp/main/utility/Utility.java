package tech.android.tcmp13.robotsapp.main.utility;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import tech.android.tcmp13.robotsapp.main.entities.robot.Robot;

import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_BRAND;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_NAME;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_TYPE;

/**
 * Created by tcmp13-t on 12/11/2016.
 *
 * Holds static helper functions
 */
public class Utility {

    @NonNull
    public static ContentValues generateContentValues(Robot robot) {

        ContentValues values = new ContentValues();
        values.put(COLUMNS_NAME, robot.getName());
        values.put(COLUMNS_BRAND, robot.getBrand());
        values.put(COLUMNS_TYPE, robot.getType().getRawValue());
        return values;
    }
}
