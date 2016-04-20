package jt.autismtracks;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        // object item based on the position
        final Rewards_class objectItem = obj.get(position);

        TextView TitleItem = (TextView) convertView.findViewById(R.id.TitleName);
        TitleItem.setText(objectItem.getTitle());

        ImageView ImageItem = (ImageView) convertView.findViewById(R.id.icon_goals);
        ImageItem.setImageResource(mContext.getResources().getIdentifier(objectItem.getIcon_src(), null, "jt.autismtracks"));

        return convertView;
    }
}
