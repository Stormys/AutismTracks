package jt.autismtracks;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by chrx on 4/20/16.
 */
public class RewardsAdapter extends ArrayAdapter<Rewards_class> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Rewards_class> obj = null;

    public RewardsAdapter(Context mContext, int layoutResourceId, ArrayList<Rewards_class> data) {
        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.obj = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        final View  v = convertView;
        // object item based on the position
        final Rewards_class objectItem = obj.get(position);

        TextView TitleItem = (TextView) convertView.findViewById(R.id.TitleName);
        TitleItem.setText(objectItem.getTitle());

        TextView PointItem = (TextView) convertView.findViewById(R.id.points);
        PointItem.setText(String.valueOf(objectItem.getPoints() + " Reward Points"));

        ImageView ImageItem = (ImageView) convertView.findViewById(R.id.icon_reward);
        ImageItem.setImageResource(mContext.getResources().getIdentifier(objectItem.getIcon_src(), null, "jt.autismtracks"));

        final CheckBox temp = (CheckBox) convertView.findViewById(R.id.checkBox);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            TaskDatabase td = new TaskDatabase(mContext);
                            if (objectItem.getPoints() <= td.getPoints()) {
                                td.updatePoints(-1*objectItem.getPoints());
                                td.delete_goal(objectItem.getId());
                                remove(objectItem);
                                notifyDataSetChanged();
                            } else {
                                temp.setChecked(false);
                            }
                    }
                }, 600);
            }
        });
        return convertView;
    }
}
