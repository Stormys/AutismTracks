package jt.autismtracks;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private ArrayList<String> values = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        create_adapter();
        insert_button();
    }

    private void insert_button() {
        Button button = (Button) findViewById(R.id.NewTask);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, TaskSettings.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void create_adapter() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        writeToAdaptor();
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && !data.getStringExtra("Title").equals("")) {
            writeToAdaptor();
        }
    }

    private void writeToAdaptor() {
        String filename = "myTasks";
        FileInputStream i_stream ;
        InputStreamReader i_stream_reader;
        BufferedReader br;

        StringBuilder finalString = new StringBuilder();
        String line;
        try {
            i_stream = openFileInput(filename);
            i_stream_reader = new InputStreamReader(i_stream);
            br = new BufferedReader(i_stream_reader);
            while ((line = br.readLine()) != null) {
                finalString.append(line);
            }
            br.close();
            i_stream_reader.close();
            i_stream.close();
            adapter.add(finalString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
