package jt.autismtracks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by julian on 4/16/16.
 */
public class TaskDatabase {

    private final Context context;
    private TaskDatabaseHelper DBhelper;
    private SQLiteDatabase db;

    public TaskDatabase(Context context) {
        this.context = context;
        DBhelper = new TaskDatabaseHelper(context);
    }

    public void open() {
        db = DBhelper.getWritableDatabase();
    }

    public void insertRecord(String title, String thedate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:m a");
        Date date;
        try {
            date = formatter.parse(thedate);
            ContentValues initialValues = new ContentValues();
            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Task, title);
            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Date, date.getTime());
            initialValues.put(TaskTableContents.TaskEntry.Column_Name_Done,true);
            db.insert(TaskTableContents.TaskEntry.TABLE_NAME, null, initialValues);
        } catch (ParseException e) {
            Log.e("Date Parsing", "Unable to parse inputted date to date object");
        }
    }

    public Cursor getTasks() {
        db = DBhelper.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from " + TaskTableContents.TaskEntry.TABLE_NAME  + " ORDER BY " + TaskTableContents.TaskEntry.COLUMN_NAME_Date  + " ASC", null );
        return c;
    }

    public void delete_all() {
        db = DBhelper.getWritableDatabase();
        DBhelper.delete_all(db);
    }
}

