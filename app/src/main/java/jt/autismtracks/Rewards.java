package jt.autismtracks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Rewards extends AppCompatActivity {


    private ArrayList<Rewards_class> values = new ArrayList<Rewards_class>();
    private RewardsAdapter adapter;
    private TaskDatabase td = new TaskDatabase(this);
    private ListView lv;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_rewards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv = (TextView) findViewById(R.id.okayad);
        tv.setText(String.valueOf(td.getPoints()));
        create_adapter();
        item_clicker();
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
        lv = (ListView) findViewById(R.id.list2);
        adapter = new RewardsAdapter(this,R.layout.rewards_adapter,values);
        lv.setAdapter(adapter);
        write_list();
    }

    private void item_clicker() {
        lv = (ListView) findViewById(R.id.list2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent b = new Intent(Rewards.this,RewardSettings.class);
                b.putExtra("name",values.get(i).getTitle());
                b.putExtra("id",values.get(i).getId());
                b.putExtra("Points",values.get(i).getPoints());
                b.putExtra("Icon",values.get(i).getIcon_src());
                startActivityForResult(b,1);
            }
        });
    }

    public void onCreate_Goals() {
        Intent intent = new Intent(Rewards.this, RewardSettings.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data) {
        write_list();
    }

    private void write_list() {
        values.clear();

        Cursor results = td.getGoals();
        results.moveToFirst();

        while (results.isAfterLast() == false) {
            Rewards_class t = new Rewards_class();
            t.setId(results.getLong(0));
            t.setTitle(results.getString(results.getColumnIndex(RewardsTableContents.RewardsEntry.COLUMN_NAME_Reward)));
            t.setPoints(results.getInt(2));
            t.setIcon_src(results.getString(3));
            adapter.add(t);
            results.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }
}
