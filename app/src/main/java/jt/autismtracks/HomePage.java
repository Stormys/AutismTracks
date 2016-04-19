package jt.autismtracks;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class HomePage extends ListActivity {

    private ArrayList<Task> values = new ArrayList<Task>();
    private TaskAdapter adapter;
    private TaskDatabase td = new TaskDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        create_adapter();
        insert_create_button();
        insert_delete_button();
        create_show_checked_button();
        item_clickers();
    }

    private void insert_create_button() {
        Button button = (Button) findViewById(R.id.NewTask);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, TaskSettings.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void create_show_checked_button() {
        Button button = (Button) findViewById(R.id.show_checked);
        button.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
             cursor_to_tasks(td.getChecked());
         }
        });
    }

    private void insert_delete_button() {
        Button button = (Button) findViewById(R.id.delete_all);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                values.clear();
                adapter.notifyDataSetChanged();
                td.delete_all();
            }
        });
    }

    private void create_adapter() {
        adapter = new TaskAdapter(this, R.layout.list_view_row_item, values);
        setListAdapter(adapter);
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
                Log.e("Hello","What");
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
            t.setDate(results.getLong(2));
            t.setDone(results.getInt(3));
            adapter.add(t);
            results.moveToNext();
        }
    }
}
