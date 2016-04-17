package jt.autismtracks;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomePage extends ListActivity {

    private ArrayList<Task> values = new ArrayList<Task>();
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        create_adapter();
        insert_create_button();
        insert_delete_button();
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

    private void insert_delete_button() {
        Button button = (Button) findViewById(R.id.delete_all);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                values.clear();
                adapter.notifyDataSetChanged();
                deleteFile("myTasks");
            }
        });
    }

    private void create_adapter() {
        adapter = new TaskAdapter(this, R.layout.list_view_row_item, values);
        setListAdapter(adapter);
        writeToAdaptor();
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && !data.getStringExtra("Title").equals("")) {
            Task t = new Task();
            t.setTitle(data.getStringExtra("Title"));
            t.setDate(data.getStringExtra("Date"));
            adapter.add(t);
        }
    }

    private void writeToAdaptor() {
        String filename = "myTasks";
        FileInputStream i_stream ;
        InputStreamReader i_stream_reader;
        BufferedReader br;

        String line;
        String[] tokens;
        try {
            i_stream = openFileInput(filename);
            i_stream_reader = new InputStreamReader(i_stream);
            br = new BufferedReader(i_stream_reader);
            while ((line = br.readLine()) != null) {
                tokens = line.split(";");
                Task t = new Task();
                t.setTitle(tokens[0]);
                t.setDate(tokens[1]);
                adapter.add(t);
            }
            br.close();
            i_stream_reader.close();
            i_stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
