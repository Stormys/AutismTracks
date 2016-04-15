package jt.autismtracks;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chrx on 4/14/16.
 */
public class Task {
    // private variables
    private String title;
    private Date date;

    // constructors
    public Task() {

    }
    public Task(String title) {
        this.title = title;
    }

    // setters and getters
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setDate(String dateAsString) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        try {
            date = formatter.parse(dateAsString);
        } catch (ParseException e) {
            Log.e("Date Parsing", "Unable to parse inputted date to date object");
        }
    }

    public Date getDate() {
        return date;
    }
}
