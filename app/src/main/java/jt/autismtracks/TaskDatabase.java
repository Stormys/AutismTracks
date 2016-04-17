package jt.autismtracks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void insertRecord(String title, String date) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Task, title);
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Date, date);
        db.insert(TaskTableContents.TaskEntry.TABLE_NAME, null, initialValues);
    }

    public Cursor getTasks() {
        db = DBhelper.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from " + TaskTableContents.TaskEntry.TABLE_NAME, null );
        return c;
    }

    public void delete_all() {
        db = DBhelper.getWritableDatabase();
        DBhelper.delete_all(db);
    }
}

