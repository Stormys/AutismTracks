package jt.autismtracks;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomePage extends AppCompatActivity {

    private ArrayList<Task> values = new ArrayList<Task>();
    private TaskAdapter adapter;
    private TaskDatabase td = new TaskDatabase(this);
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        create_adapter();
        create_toolbar();
        create_show_checked_button();
        item_clickers();
    }

    private void create_toolbar() {
        getSupportActionBar().setTitle("All Tasks");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.add_task_menu:
                showInputDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void create_show_checked_button() {
        final ToggleButton button = (ToggleButton) findViewById(R.id.show_checked);
        button.setText("Show Checked");
        button.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
             if (button.isChecked()) {
                 cursor_to_tasks(td.getChecked());
                 button.setText("Hide Checked");
             } else {
                 button.setText("Show Checked");
                 cursor_to_tasks(td.getTasks());
             }
         }
        });
    }

    private void create_adapter() {
        lv = (ListView) findViewById(android.R.id.list);
        adapter = new TaskAdapter(this, R.layout.list_view_row_item, values);
        lv.setAdapter(adapter);
        write_list();
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            write_list();
        }
    }

    private void write_list() {
        cursor_to_tasks(td.getTasks());
    }

    private void item_clickers() {
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent b = new Intent(HomePage.this, TaskSettings.class);
                TextView temp = (TextView) view.findViewById(R.id.TitleName);
                TextView date = (TextView) view.findViewById(R.id.Date);
                ToggleButton alarm = (ToggleButton) view.findViewById(R.id.alarmtoggle);
                b.putExtra("Title",temp.getText().toString());
                b.putExtra("Date",values.get(i).getDate().toString());
                b.putExtra("Alarm",values.get(i).getAlarm());
                b.putExtra("Points",values.get(i).getPoints());
                startActivity(b);
            }
        });
    }

    private void cursor_to_tasks(Cursor results) {
        adapter.clear();
        results.moveToFirst();

        while (results.isAfterLast() == false) {
            Task t = new Task();
            t.setRowId(results.getLong(0));
            t.setTitle(results.getString(results.getColumnIndex(TaskTableContents.TaskEntry.COLUMN_NAME_Task)));
            if (results.getLong(2) != 0)
                t.setDate(results.getLong(2));
            t.setDone(results.getInt(3));
            t.setAlarm(results.getInt(results.getColumnIndex(TaskTableContents.TaskEntry.COLUMN_NAME_Alarm)));
            t.setPoints(results.getInt(results.getColumnIndex(TaskTableContents.TaskEntry.COLUMN_NAME_Points)));
            adapter.add(t);
            results.moveToNext();
        }
    }

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(HomePage.this);
        View promptView = layoutInflater.inflate(R.layout.new_task, null,false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.new_task_et);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!editText.getText().toString().equals("")) {
                            Task t = new Task();
                            t.setTitle(editText.getText().toString());
                            t.setPoints(50);
                            values.add(0,t);
                            adapter.notifyDataSetChanged();
                            td.insertEmptyTask(editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
