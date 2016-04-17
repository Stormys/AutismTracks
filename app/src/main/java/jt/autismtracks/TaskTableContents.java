package jt.autismtracks;

import android.provider.BaseColumns;

/**
 * Created by julian on 4/16/16.
 */
public class TaskTableContents {
    public TaskTableContents() {}

    /* Inner class that defines the table contents */
    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tasks";
        public static final String COLUMN_NAME_Task = "task";
        public static final String COLUMN_NAME_Date = "date";
    }
}
