package jt.autismtracks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HeadQuarter extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<String>();
    private FloatingActionButton fab, fab1, fab2;
    private Animation rotate_forward, rotate_backwards, fab_open, fab_close;
    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_quarter);
        set_list_adapter();
        set_fab_clicker();
    }

    private void set_list_adapter() {
        ListView lv = (ListView) findViewById(R.id.listView);
        items.add("Task List");
        items.add("Goals");
        items.add("Rewards");

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);
    }

    private void set_fab_clicker() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.add_task);
        fab2 = (FloatingActionButton) findViewById(R.id.add_goal);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backwards = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backwards);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClicked) {
                    fab.startAnimation(rotate_backwards);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isClicked = false;
                } else {
                    fab.startAnimation(rotate_forward);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isClicked = true;
                }
            }

        });
    }
}