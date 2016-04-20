package jt.autismtracks;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by julian on 4/19/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds = {R.drawable.document, R.drawable.mini_shower_icon,R.drawable.blackboard, R.drawable.bath, R.drawable.hamburger,
                                    R.drawable.strip, R.drawable.coins, R.drawable.medal};
    public int p = 0;
    private View v = null;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v != null)
                    v.setBackgroundResource(0);
                p = position;
                v = view;
                view.setBackgroundResource(R.drawable.b_key);
            }
        });
        return imageView;
    }
}
