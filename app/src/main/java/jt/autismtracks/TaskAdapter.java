package jt.autismtracks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by julian on 4/15/16.
 */
public class TaskAdapter extends ArrayAdapter<Task>  {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Task> obj = null;

    public TaskAdapter(Context mContext, int layoutResourceId, ArrayList<Task> data) {
        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.obj = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        final Task objectItem = obj.get(position);

        TextView TitleItem = (TextView) convertView.findViewById(R.id.TitleName);
        TitleItem.setText(objectItem.getTitle());

        TextView DateItem = (TextView) convertView.findViewById(R.id.Date);
        DateItem.setText(objectItem.getDate());

        final CheckBox CheckItem = (CheckBox) convertView.findViewById(R.id.checkBox);
        CheckItem.setChecked(objectItem.getDone());
        CheckItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectItem.setDone(CheckItem.isChecked());
                TaskDatabase td = new TaskDatabase(mContext);
                td.update_checked(objectItem.getRowId(),objectItem.getDone());
                obj.clear();
                Cursor results = td.getTasks();
                results.moveToFirst();

                while (results.isAfterLast() == false) {
                    Task t = new Task();
                    t.setRowId(results.getLong(0));
                    t.setTitle(results.getString(results.getColumnIndex(TaskTableContents.TaskEntry.COLUMN_NAME_Task)));
                    t.setDate(results.getLong(2));
                    t.setDone(results.getInt(3));
                    add(t);
                    results.moveToNext();
                }
            }
        });
        return convertView;
    }
}
