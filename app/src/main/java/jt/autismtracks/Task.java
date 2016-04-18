package jt.autismtracks;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chrx on 4/14/16.
 */
public class Task {
    // private variables
    private String title = null;
    private Date date = null;
    private boolean Done = false;
    private long rowId;

    // constructors
    public Task() {

    }
    public Task(String title) {
        this.title = title;
    }

    // setters and getters
    public void setTitle(String title) { this.title = title; }
    public String getTitle() {
        if (title != null)
            return title;
        return "New Task";
    }

    public void setDate(String dateAsString) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:m a");
        try {
            date = formatter.parse(dateAsString);
        } catch (ParseException e) {
            Log.e("Date Parsing", "Unable to parse inputted date to date object");
        }
    }

    public void setDate(long n) {
        date = new Date(n);
    }

    public String getDate() {
        if (date != null) {
            return date.toString();
        }
        return "No Date";
    }

    public void setDone(int temp) {
        if (temp == 1)
            Done = true;
        else
            Done = false;
    }

    public void setDone(boolean temp) {
        Done = temp;
    }

    public boolean getDone() {
        return Done;
    }

    public void setRowId(long id) {
        rowId = id;
    }

    public long getRowId() {
        return rowId;
    }
}
