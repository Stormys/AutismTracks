package jt.autismtracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class RewardSettings extends AppCompatActivity {
    private SeekBar Skb;
    private TextView pointslabel;
    private TextView new_reward;
    private ImageButton ib;
    private TextView hidden;
    private long test2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_rewards_settings);
        new_reward = (TextView) findViewById(R.id.new_reward);
        Skb = (SeekBar) findViewById(R.id.points_slider);
        pointslabel = (TextView) findViewById(R.id.points_reward);
        ib = (ImageButton) findViewById(R.id.icon_button);
        hidden = (TextView) findViewById(R.id.hidden);
        test2 = getIntent().getLongExtra("id",-1);
        create_toolbar();
        SeekBar();
        create_icon_button();
        checkIntent();
        create_delete_button();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        TaskDatabase td = new TaskDatabase(this);
        if (item.getItemId() == android.R.id.home && !new_reward.getText().toString().equals("") && Skb.getProgress() != 0 && test2 == -1)  {
            td.open();
            td.insertGoal(new_reward.getText().toString(),Skb.getProgress(),hidden.getText().toString());
        } else if (item.getItemId() == android.R.id.home && !new_reward.getText().toString().equals("") && Skb.getProgress() != 0) {
            td.open();
            td.update_goals(test2,new_reward.getText().toString(),Skb.getProgress(),hidden.getText().toString());
        }
        finish(); // close this activity and return to preview activity (if there is any)

        return super.onOptionsItemSelected(item);
    }

    private void checkIntent() {
        if (getIntent().getStringExtra("name") != null)
            new_reward.append(getIntent().getStringExtra("name"));
        Skb.setProgress(getIntent().getIntExtra("Points",0));
        pointslabel.setText(getIntent().getIntExtra("Points",0) + "/2000");
        if (getIntent().getStringExtra("Icon") != null) {
            ib.setImageResource(getResources().getIdentifier(getIntent().getStringExtra("Icon"),null, "jt.autismtracks"));
            hidden.setText(getIntent().getStringExtra("Icon"));
        }
    }

    private void SeekBar() {
        Skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointslabel.setText(String.valueOf(seekBar.getProgress()) + "/2000");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void create_toolbar() {
        getSupportActionBar().setTitle("Reward Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void create_icon_button() {
        ib = (ImageButton) findViewById(R.id.icon_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TaskSettings.IconPickerFragment();
                newFragment.show(getSupportFragmentManager(),"IconPicker");
            }
        });
    }

    private void create_delete_button() {
        Button test = (Button) findViewById(R.id.delete);
        if ( test2 == -1) {
            test.setVisibility(View.INVISIBLE);
            test.setEnabled(false);
        } else {
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDatabase td = new TaskDatabase(getApplicationContext());
                    td.open();
                    td.delete_goal(test2);
                    Intent i = new Intent();
                    setResult(2,i);
                    finish();
                }
            });
        }
        }
}
