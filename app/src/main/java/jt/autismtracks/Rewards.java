package jt.autismtracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class Rewards extends AppCompatActivity {


    private ArrayList<Rewards_class> values = new ArrayList<Rewards_class>();
    private RewardsAdapter adapter;
    private TaskDatabase td = new TaskDatabase(this);
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        create_adapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.add_task_menu:
                onCreate_Goals();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page_menu,menu);
        return true;
    }

    public void create_adapter() {
        lv = (ListView) findViewById(R.id.goals_view);
        Rewards_class test1 = new Rewards_class();
        test1.setTitle("F WAI");
        test1.setIcon_src("@drawable/document");
        values.add(test1);
        adapter = new RewardsAdapter(this,R.layout.rewards_adapter,values);
        lv.setAdapter(adapter);
    }

    public void onCreate_Goals() {
        Intent intent = new Intent(Rewards.this, RewardSettings.class);
        startActivity(intent);
    }
}
