package jt.autismtracks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by julian on 4/19/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds2 = {R.drawable.agenda, R.drawable.apple, R.drawable.atom, R.drawable.backpack,
    R.drawable.blackboard, R.drawable.briefcase, R.drawable.calculator, R.drawable.calendar, R.drawable.chemistry,
    R.drawable.clock_1, R.drawable.compass, R.drawable.crayons,
    R.drawable.desk_lamp, R.drawable.diploma_1, R.drawable.file,
    R.drawable.grades, R.drawable.headphones, R.drawable.high_school, R.drawable.homework, R.drawable.notebook_1, R.drawable.padlock, R.drawable.paper_plane, R.drawable.pencil_2, R.drawable.pencil_3,
    R.drawable.protractor, R.drawable.school_bus, R.drawable.swimming_pool,
    R.drawable.trophy, R.drawable.violin, R.drawable.watercolor, R.drawable.armchair, R.drawable.basket, R.drawable.bath,
    R.drawable.coffee_machine, R.drawable.hanger, R.drawable.ironing_board, R.drawable.microwave, R.drawable.stove, R.drawable.towel, R.drawable.washing_machine, R.drawable.apple, R.drawable.banana,
    R.drawable.bread, R.drawable.canned_food_1, R.drawable.chicken, R.drawable.chocolate, R.drawable.fish, R.drawable.french_fries, R.drawable.fried_egg, R.drawable.hamburger, R.drawable.lemon,
    R.drawable.lettuce, R.drawable.muffin, R.drawable.toaster, R.drawable.tomato, R.drawable.basketball, R.drawable.football, R.drawable.skipping_rope, R.drawable.sneakers,
    R.drawable.stationary_bike, R.drawable.stopwatch, R.drawable.treadmill, R.drawable.water, R.drawable.weight_1, R.drawable.weight, R.drawable.whistle, R.drawable.cart,
    R.drawable.garden_fork, R.drawable.leaves, R.drawable.mower, R.drawable.pail, R.drawable.sprayer, R.drawable.sprout, R.drawable.watering_can,
    R.drawable.aids, R.drawable.cardiogram, R.drawable.dropper, R.drawable.emergency_kit, R.drawable.hospital, R.drawable.medicine_2, R.drawable.pills, R.drawable.syringe,
    R.drawable.tablets, R.drawable.test_tube };

    public ArrayList<Bitmap> mThumbIds;
    public int p = 0;
    private View v = null;
    public ImageAdapter(Context c) {
        mContext = c;
        mThumbIds = new ArrayList<Bitmap>();
        for (Integer v : mThumbIds2) {
            mThumbIds.add(decodeSampledBitmapFromResource(mContext.getResources(), v, 20, 20));
        }
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
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mThumbIds.get(position));

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
