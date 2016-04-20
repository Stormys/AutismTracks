package jt.autismtracks;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskSettings extends AppCompatActivity {

    private EditText temp;
    private TextView tvDate;
    private TextView tvTime;
    private ToggleButton alarm;
    private SeekBar Skb;
    private TextView pointslabel;
    private ImageButton ib;
    private TextView saveddraw;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private final static Calendar c = Calendar.getInstance();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_settings);
        temp = (EditText) findViewById(R.id.new_task);
        tvDate = (TextView) findViewById(R.id.date);
        tvTime = (TextView) findViewById(R.id.time);
        saveddraw = (TextView) findViewById(R.id.hidden);
        ib = (ImageButton) findViewById(R.id.icon_button);
        alarm = (ToggleButton) findViewById(R.id.alarmtoggle);
        Skb = (SeekBar) findViewById(R.id.seekBar);
        pointslabel = (TextView) findViewById(R.id.pointslabel);
        check_intent();
        create_toolbar();
        create_icon_button();
        SeekBar();
    }

    private void create_icon_button() {
        ib = (ImageButton) findViewById(R.id.icon_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new IconPickerFragment();
                newFragment.show(getSupportFragmentManager(),"TimePicker");
            }
        });
    }

    private void SeekBar() {
        Skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointslabel.setText(String.valueOf(seekBar.getProgress()) + "/100");
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
        getSupportActionBar().setTitle("Task Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void check_intent() {
        if (getIntent().getStringExtra("Title") != null)
            temp.append(getIntent().getStringExtra("Title"));
        if (getIntent().getStringExtra("Date") != null) {
            tvDate.setText(getIntent().getStringExtra("Date"));
        } if (getIntent().getStringExtra("Src") != null) {
            ib.setImageResource(getResources().getIdentifier(getIntent().getStringExtra("Src"),null, "jt.autismtracks"));
        }
        alarm.setChecked(getIntent().getBooleanExtra("Alarm",false));
        Skb.setProgress(getIntent().getIntExtra("Points",50));
        pointslabel.setText(getIntent().getIntExtra("Points",50) + "/100");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Log.e("What",String.valueOf(ib.getResources()));
            if (getIntent().getStringExtra("from").equals("head"))
                writeInternal();
            else {
                //edit it instead
            }
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAlarm() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:m a");
        Date date;
        try {
            date = formatter.parse(tvDate.getText().toString() + " " +  tvTime.getText().toString());
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            // add alarm to alarm manager
            Intent myIntent = new Intent(TaskSettings.this, AlarmReceiver.class);
            myIntent.putExtra("Title", temp.getText().toString());
            pendingIntent = PendingIntent.getActivity(TaskSettings.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime() , pendingIntent);
        } catch (ParseException e) {

        }
    }
    public void writeInternal() {
        TaskDatabase td = new TaskDatabase(this);
        td.open();
        td.insertRecord((!temp.getText().toString().equals("") ? temp.getText().toString() : "New Task"), tvDate.getText().toString() + " " +  tvTime.getText().toString(),alarm.isChecked(),Skb.getProgress(),saveddraw.getText().toString());
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(),"TimePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tvDate = (TextView) getActivity().findViewById(R.id.date);
            tvDate.setText(MONTHS[month] + " " + String.valueOf(day) + ", " + String.valueOf(year));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int day = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),this,hour,day,false);
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            TextView tvTime = (TextView) getActivity().findViewById(R.id.time);
            if (hour > 12) {
                tvTime.setText(hour % 12 + ":" + String.format("%02d",minute) + " PM");
            } else {
                tvTime.setText(hour + ":" + String.format("%02d",minute) + " AM");
            }
        }
    }
    public static class IconPickerFragment extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.icon_picker, container,
                    false);
            final TextView savedDraw = (TextView) getActivity().findViewById(R.id.hidden);
            getDialog().setTitle("Icon Chooser");
            final GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(getActivity()));

            Button b1 = (Button) rootView.findViewById(R.id.button2);
            Button b2 = (Button) rootView.findViewById(R.id.button3);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton ib = (ImageButton) getActivity().findViewById(R.id.icon_button);
                    ImageAdapter test = (ImageAdapter) gridview.getAdapter();
                    ib.setImageResource(test.mThumbIds[test.p]);
                    savedDraw.setText(test.mThumbIds[test.p].toString());
                    dismiss();
                }
            });

            return rootView;
        }
    }
}
