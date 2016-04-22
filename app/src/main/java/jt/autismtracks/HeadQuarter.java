package jt.autismtracks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class HeadQuarter extends AppCompatActivity {

    private FloatingActionButton fab, fab1, fab2;
    private Animation rotate_forward, rotate_backwards, fab_open, fab_close;
    private TextView add_task_tv, add_goals_tv;
    private GridView lv;
    private boolean isClicked = false;
    private TaskDatabase td = new TaskDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_quarter);
        getSupportActionBar().setTitle("Julian Tuminaro");
        set_list_adapter();
        set_fab_clicker();
        set_task_list_clicker();
        td.check();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.open:
                Intent intent = new Intent(HeadQuarter.this, AppSettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void set_list_adapter() {
        lv = (GridView) findViewById(R.id.listView);

        lv.setAdapter(new ImageAdapter2(this));
    }

    private void set_fab_clicker() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.add_task);
        fab2 = (FloatingActionButton) findViewById(R.id.add_goal);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backwards = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backwards);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        add_task_tv = (TextView) findViewById(R.id.add_task_tv);
        add_goals_tv = (TextView) findViewById(R.id.add_goal_tv);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClicked) {
                    fab.startAnimation(rotate_backwards);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    add_task_tv.startAnimation(fab_close);
                    add_goals_tv.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isClicked = false;
                } else {
                    fab.startAnimation(rotate_forward);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    add_task_tv.startAnimation(fab_open);
                    add_goals_tv.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isClicked = true;
                }
            }

        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HeadQuarter.this, TaskSettings.class);
                intent.putExtra("from","head");
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HeadQuarter.this,RewardSettings.class);
                intent.putExtra("from","head");
                startActivity(intent);
            }
        });
    }

    private void set_task_list_clicker() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(HeadQuarter.this, HomePage.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(HeadQuarter.this, Rewards.class);
                    startActivity(intent);
                } else if (position == 2) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1693857870868096"));
                        startActivity(intent);
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/habitracks")));
                    }
                } else if (position == 3) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("twitter://user?screen_name=habitracks"));
                        startActivity(intent);

                    }catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/habitracks")));
                    }

                } else if (position == 4) {
                    Uri uri = Uri.parse("http://instagram.com/_u/habitracks");
                    Intent insta = new Intent(Intent.ACTION_VIEW, uri);
                    insta.setPackage("com.instagram.android");

                    if (isIntentAvailable(getApplicationContext(), insta)){
                        startActivity(insta);
                    } else{
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/habitracks")));
                    }
                } else if (position == 5) {
                    Intent intent = new Intent(HeadQuarter.this, AppSettingsActivity.class);
                    startActivity(intent);
                }
            }
        } );
    }


    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}