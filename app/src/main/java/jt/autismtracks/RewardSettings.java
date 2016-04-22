package jt.autismtracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class RewardSettings extends AppCompatActivity {
    private SeekBar Skb;
    private TextView pointslabel;
    private ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_settings);
        Skb = (SeekBar) findViewById(R.id.points_slider);
        pointslabel = (TextView) findViewById(R.id.points_reward);
        ib = (ImageButton) findViewById(R.id.icon_button);
        create_toolbar();
        SeekBar();
        create_icon_button();
        create_delete_button();
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
        final long test2 = getIntent().getLongExtra("id",-1);
        if ( test2 == -1) {
            test.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            test.setEnabled(false);
        } else {
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDatabase td = new TaskDatabase(getApplicationContext());
                    td.open();
                    td.delete(test2);
                    Intent i = new Intent();
                    setResult(2,i);
                    finish();
                }
            });
        }
    }
}
