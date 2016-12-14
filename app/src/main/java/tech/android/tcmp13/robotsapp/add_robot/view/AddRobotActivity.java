package tech.android.tcmp13.robotsapp.add_robot.view;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract;
import tech.android.tcmp13.robotsapp.main.utility.AppConstants;
import tech.android.tcmp13.robotsapp.R;
import tech.android.tcmp13.robotsapp.main.entities.robot.Robot;
import tech.android.tcmp13.robotsapp.main.entities.robot.RobotType;
import tech.android.tcmp13.robotsapp.main.utility.Utility;

import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.*;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public class AddRobotActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ADD_ROBOT_LOADER_ID = 1699;

    private Uri uri;

    private EditText nameInput;
    private EditText brandInput;
    private EditText typeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        uri = getIntent().getData();
        setupUi();
        if (isEdit())
            getLoaderManager().initLoader(ADD_ROBOT_LOADER_ID, null, this);
    }

    private void setupUi() {

        setContentView(R.layout.activity_add_robot);
        nameInput = (EditText) findViewById(R.id.addRobotNameInput);
        brandInput = (EditText) findViewById(R.id.addRobotBrandInput);
        typeInput = (EditText) findViewById(R.id.addRobotTypeInput);
        if (isEdit())
            setTitle("Edit Robot");
        else
            setTitle("Add Robot");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_add_robot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveRobotActionItem:
                Robot robot = generateRobotFromInput();
                if (isEdit())
                    updateRobotAndFinish(robot);
                else
                    addNewRobotAndFinish(robot);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewRobotAndFinish(Robot robot) {

        Uri resultUri = getContentResolver().insert(ROBOTS_CONTENT_URI, Utility.generateContentValues(robot));
        if (resultUri != null)
            Toast.makeText(this, "Hallelujah!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateRobotAndFinish(Robot robot) {

        int updatedRows = getContentResolver().update(uri, Utility.generateContentValues(robot), null, null);
        if (updatedRows != 0)
            Toast.makeText(this, "Hallelujah Update!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Robot generateRobotFromInput() {

        String name = nameInput.getText().toString();
        String brand = brandInput.getText().toString();
        RobotType type = RobotType.fromRawValue(Integer.valueOf(typeInput.getText().toString()));
        return new Robot(name, brand, type);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        switch (id) {
            case ADD_ROBOT_LOADER_ID:
                return new CursorLoader(this, //The Context calling (attached to) the manager
                        uri, //The uri for query a specific id
                        new String[]{_ID, COLUMNS_NAME, COLUMNS_BRAND, COLUMNS_TYPE}, //Projection
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        //Check for valid result
        if (cursor == null || cursor.getCount() < 1)
            return;
        if (!cursor.moveToFirst())
            return;

        //Fetch the info
        String name = cursor.getString(cursor.getColumnIndex(COLUMNS_NAME));
        String brand = cursor.getString(cursor.getColumnIndex(COLUMNS_BRAND));
        int type = cursor.getInt(cursor.getColumnIndex(COLUMNS_TYPE));

        nameInput.setText(name);
        brandInput.setText(brand);
        typeInput.setText(String.valueOf(type));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        nameInput.setText("");
        brandInput.setText("");
        typeInput.setText("");
    }

    private boolean isEdit() {

        return uri != null;
    }
}
