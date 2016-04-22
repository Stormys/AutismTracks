package jt.autismtracks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by julian on 4/22/16.
 */
public class ImageAdapter2 extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds2 = {R.drawable.home_agenda,R.drawable.home_trophy, R.drawable  .fb,R.drawable.twitter,R.drawable.instagram};
    private float scale;
    private int pixels;
    private final int dps = 90;

    public ArrayList<Bitmap> mThumbIds;
    public int p = 0;
    private View v = null;
    public ImageAdapter2(Context c) {
        mContext = c;
        mThumbIds = new ArrayList<Bitmap>();
        for (Integer v : mThumbIds2) {
            mThumbIds.add(decodeSampledBitmapFromResource(mContext.getResources(), v, 100, 100));
        }
        scale = mContext.getResources().getDisplayMetrics().density;
        pixels = (int) (dps * scale + 0.5f);
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

            imageView.setLayoutParams(new GridView.LayoutParams(pixels, pixels));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mThumbIds.get(position));

        return imageView;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
