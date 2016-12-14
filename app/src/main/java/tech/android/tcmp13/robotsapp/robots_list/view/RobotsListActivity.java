package tech.android.tcmp13.robotsapp.robots_list.view;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tech.android.tcmp13.robotsapp.R;
import tech.android.tcmp13.robotsapp.add_robot.view.AddRobotActivity;
import tech.android.tcmp13.robotsapp.robots_list.control.RobotsCursorAdapter;

import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_BRAND;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_NAME;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.COLUMNS_TYPE;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.ROBOTS_CONTENT_URI;
import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry._ID;

public class RobotsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int QUERY_ALL_LOADER_ID = 169;

    private RobotsCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupUi();
        getLoaderManager().initLoader(QUERY_ALL_LOADER_ID, null, this);
    }

    private void setupUi() {
        setContentView(R.layout.activity_main);
        ListView robotsListView = (ListView) findViewById(R.id.robotsListView);
        adapter = getRobotsCursorAdapter();
        robotsListView.setAdapter(adapter);
        robotsListView.setOnItemClickListener(this);
    }

    private RobotsCursorAdapter getRobotsCursorAdapter() {

        return new RobotsCursorAdapter(this, null);
    }

    /**
     * @param cursor if null will clear the adapter.
     */
    private void updateAdapterCursor(@Nullable Cursor cursor) {
        Cursor oldCursor = adapter.swapCursor(cursor);
        if (oldCursor != null)
            oldCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addRobotActionItem:
                startActivity(new Intent(this, AddRobotActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        switch (id) {
            case QUERY_ALL_LOADER_ID:
                return new CursorLoader(this, //The Context calling (attached to) the manager
                        ROBOTS_CONTENT_URI, //The uri for query all
                        new String[]{_ID, COLUMNS_NAME, COLUMNS_BRAND, COLUMNS_TYPE}, //Projection
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        updateAdapterCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        updateAdapterCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

        Intent intent = new Intent(this, AddRobotActivity.class);
        intent.setData(ContentUris.withAppendedId(ROBOTS_CONTENT_URI, id));
        startActivity(intent);
    }
}
