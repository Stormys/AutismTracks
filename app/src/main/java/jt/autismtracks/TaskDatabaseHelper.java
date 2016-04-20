package jt.autismtracks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by julian on 4/16/16.
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TaskDatabase.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String Date_TYPE = " long";
    private static final String INT_TYPE = " int";
    private static final String Bool_Type = " boolean";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskTableContents.TaskEntry.TABLE_NAME + " (" +
                    TaskTableContents.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskTableContents.TaskEntry.COLUMN_NAME_Task + TEXT_TYPE + COMMA_SEP +
                    TaskTableContents.TaskEntry.COLUMN_NAME_Date + Date_TYPE + COMMA_SEP +
                    TaskTableContents.TaskEntry.Column_Name_Done + Bool_Type + COMMA_SEP +
                    TaskTableContents.TaskEntry.COLUMN_NAME_Points + INT_TYPE + COMMA_SEP +
                    TaskTableContents.TaskEntry.COLUMN_NAME_Alarm + Bool_Type + COMMA_SEP +
                    TaskTableContents.TaskEntry.COLUMN_NAME_Icon_Src + TEXT_TYPE +" )";
    private static final String SQL_CREATE_GOALS =
            "CREATE TABLE " + RewardsTableContents.RewardsEntry.TABLE_NAME + " (" +
                    RewardsTableContents.RewardsEntry._ID + " INTEGER PRIMARY KEY," +
                    RewardsTableContents.RewardsEntry.COLUMN_NAME_Reward + TEXT_TYPE + COMMA_SEP +
                    RewardsTableContents.RewardsEntry.COLUMN_NAME_POINTS + INT_TYPE + COMMA_SEP +
                    RewardsTableContents.RewardsEntry.COLUMN_NAME_ICON + TEXT_TYPE +  ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskTableContents.TaskEntry.TABLE_NAME;

    private static final String SQL_DELETE_GOALS =
            "DROP TABLE IF EXISTS" + RewardsTableContents.RewardsEntry.TABLE_NAME;

    public TaskDatabaseHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_GOALS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_GOALS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void delete_all(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_GOALS);
        onCreate(db);
    }

}
