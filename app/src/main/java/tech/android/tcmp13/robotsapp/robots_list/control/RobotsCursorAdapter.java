package tech.android.tcmp13.robotsapp.robots_list.control;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tech.android.tcmp13.robotsapp.R;
import tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract;

import static tech.android.tcmp13.robotsapp.main.robots_provider.RobotsContract.RobotEntry.*;

/**
 * Created by tcmp13-t on 12/11/2016.
 */
public class RobotsCursorAdapter extends CursorAdapter {


    public RobotsCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0); //We manage the update to the cursor so no flags.
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_robot, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.nameTextView = (TextView) view.findViewById(R.id.robotItemTitle);
        holder.brandTextView = (TextView) view.findViewById(R.id.robotItemBrand);
        holder.typeTextView = (TextView) view.findViewById(R.id.robotItemType);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Init the view holder like we know
        ViewHolder holder = (ViewHolder) view.getTag();

        //Get the info from the cursor
        String name = cursor.getString(cursor.getColumnIndex(COLUMNS_NAME));
        String brand = cursor.getString(cursor.getColumnIndex(COLUMNS_BRAND));
        int type = cursor.getInt(cursor.getColumnIndex(COLUMNS_TYPE));

        //Update the ui
        holder.nameTextView.setText(name);
        holder.brandTextView.setText(brand);
        holder.typeTextView.setText(String.valueOf(type));
    }

    private class ViewHolder {

        TextView nameTextView;
        TextView brandTextView;
        TextView typeTextView;
    }
}
