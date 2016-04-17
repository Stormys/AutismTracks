package jt.autismtracks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import java.io.FileOutputStream;
import java.util.Calendar;

public class TaskSettings extends AppCompatActivity {

    private EditText temp;
    private TextView tvDate;
    private TextView tvTime;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private final static Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_settings);
        temp = (EditText) findViewById(R.id.new_task);
        tvDate = (TextView) findViewById(R.id.date);
        tvTime = (TextView) findViewById(R.id.time);

        tvDate.setText(MONTHS[c.get(Calendar.MONTH)] + " " + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + ", " + String.valueOf(c.get(Calendar.YEAR)));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) {
            tvTime.setText(hour % 12 + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " PM");
        } else {
            tvTime.setText(hour + ":" + String.format("%02d",c.get(Calendar.MINUTE)) + " AM");
        }

        create_toolbar();
        create_submit_button();
    }

    protected void create_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void create_submit_button() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();

                i.putExtra("Title", temp.getText().toString());
                i.putExtra("Date", tvDate.getText().toString() + " " + tvTime.getText().toString());
                setResult(RESULT_OK, i);
                writeInternal();
                finish();
            }
        });
    }

    public void writeInternal() {
        TaskDatabase td = new TaskDatabase(this);
        td.open();
        td.insertRecord((!temp.getText().toString().equals("") ? temp.getText().toString() : "New Task"), tvDate.getText().toString() + " " +  tvTime.getText().toString());

//        String filename = "myTasks";
//        FileOutputStream o_stream;
//
//        try {
//            o_stream = openFileOutput(filename, Context.MODE_PRIVATE | MODE_APPEND);
//            o_stream.write(((!temp.getText().toString().equals("") ? temp.getText().toString() : "New Task") + ";" + tvDate.getText().toString() + " " +  tvTime.getText().toString() + "\n").getBytes());
//            o_stream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

}
