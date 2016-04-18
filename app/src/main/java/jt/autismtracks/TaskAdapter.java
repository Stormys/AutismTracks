package jt.autismtracks;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        Task objectItem = obj.get(position);

        TextView TitleItem = (TextView) convertView.findViewById(R.id.TitleName);
        TitleItem.setText(objectItem.getTitle());

        TextView DateItem = (TextView) convertView.findViewById(R.id.Date);
        DateItem.setText(objectItem.getDate());

        return convertView;
    }
}
