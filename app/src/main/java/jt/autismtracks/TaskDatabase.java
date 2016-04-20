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

    public void insertRecord(String title, String thedate, boolean alarm, int points,String draw) {
        db = DBhelper.getWritableDatabase();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:m a");
        Date date;
        try {
            ContentValues initialValues = new ContentValues();
            if (thedate != null) {
                date = formatter.parse(thedate);
                initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Date, date.getTime());
            }

            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Task, title);
            initialValues.put(TaskTableContents.TaskEntry.Column_Name_Done,false);
            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Alarm,alarm);
            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Points,points);
            initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Icon_Src,draw);
            db.insert(TaskTableContents.TaskEntry.TABLE_NAME, null, initialValues);
        } catch (ParseException e) {
            Log.e("Date Parsing", "Unable to parse inputted date to date object");
        }
    }

    public void insertGoal(String title, int points, String draw) {
        db = DBhelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_Reward, title);
        initialValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_POINTS, points);
        initialValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_ICON, draw);
        db.insert(RewardsTableContents.RewardsEntry.TABLE_NAME, null, initialValues);
    }

    public void insertEmptyTask(String title) {
        db = DBhelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Task,title);
        initialValues.put(TaskTableContents.TaskEntry.Column_Name_Done,false);
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Alarm,false);
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Points,50);
        initialValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Icon_Src,"@drawable/document");
        db.insert(TaskTableContents.TaskEntry.TABLE_NAME, null, initialValues);
    }

    public Cursor getTasks() {
        db = DBhelper.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from " + TaskTableContents.TaskEntry.TABLE_NAME  + " WHERE " + TaskTableContents.TaskEntry.Column_Name_Done + " like 0" + " ORDER BY " + TaskTableContents.TaskEntry.COLUMN_NAME_Date  + " ASC", null );
        return c;
    }

    public Cursor getGoals() {
        db = DBhelper.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from" + RewardsTableContents.RewardsEntry.TABLE_NAME, null);
        return c;
    }

    public Cursor getChecked() {
        db = DBhelper.getWritableDatabase();
        Cursor c = db.rawQuery( "select * from " + TaskTableContents.TaskEntry.TABLE_NAME + " ORDER BY " + TaskTableContents.TaskEntry.COLUMN_NAME_Date  + " ASC", null );
        return c;
    }

    public void delete_all() {
        db = DBhelper.getWritableDatabase();
        DBhelper.delete_all(db);
    }

    public void update_checked(long rowId, boolean checked) {
        db = DBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskTableContents.TaskEntry.Column_Name_Done,checked);
        String selection = TaskTableContents.TaskEntry._ID + " Like ?";
        String[] selectionArgs = { String.valueOf(rowId)};
        db.update(TaskTableContents.TaskEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    public void update_goals(long rowID, String title, int points, String draw) {
        db = DBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_Reward, title);
        contentValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_POINTS, points);
        contentValues.put(RewardsTableContents.RewardsEntry.COLUMN_NAME_ICON, draw);
        String selction = RewardsTableContents.RewardsEntry._ID + " Like ?";
        String[] selctionArgs = { String.valueOf(rowID)};
        db.update(RewardsTableContents.RewardsEntry.TABLE_NAME,
                contentValues,
                selction,
                selctionArgs);
    }

    public void update_all(long rowId, String title,String draw,int points,boolean alarm, String thedate) {
        db = DBhelper.getWritableDatabase();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:m a");
        Date date;
        try {
            ContentValues contentValues = new ContentValues();
            if (thedate != null) {
                date = formatter.parse(thedate);
                contentValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Date, date.getTime());
            }
            contentValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Task,title);
            contentValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Icon_Src,draw);
            contentValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Points,points);
            contentValues.put(TaskTableContents.TaskEntry.COLUMN_NAME_Alarm,alarm);
            String selection = TaskTableContents.TaskEntry._ID + " Like ?";
            String[] selectionArgs = { String.valueOf(rowId)};
            db.update(TaskTableContents.TaskEntry.TABLE_NAME,
                    contentValues,
                    selection,
                    selectionArgs);
        } catch (ParseException e) {

        }
    }

    public void delete(long id) {
        db.delete(TaskTableContents.TaskEntry.TABLE_NAME, TaskTableContents.TaskEntry._ID + "=" + id, null);
    }

    public void delete_goal(long id) {
        db.delete(RewardsTableContents.RewardsEntry.TABLE_NAME, RewardsTableContents.RewardsEntry._ID + "=" + id, null);
    }

}

