package jt.autismtracks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.io.FileOutputStream;
import java.util.Calendar;

public class TaskSettings extends AppCompatActivity {

    private  EditText temp;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_settings);
        temp = (EditText) findViewById(R.id.new_task);
        tvDate = (TextView) findViewById(R.id.date);
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
                i.putExtra("Date", tvDate.getText().toString());
                setResult(RESULT_OK,i);
                writeInternal();
                finish();
            }
        });
    }

    public void writeInternal() {
        String filename = "myTasks";
        FileOutputStream o_stream;

        try {
            o_stream = openFileOutput(filename, Context.MODE_PRIVATE | MODE_APPEND);
            o_stream.write((temp.getText().toString() + ";" + tvDate.getText().toString() + "\n").getBytes());
            o_stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
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
}
