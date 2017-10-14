package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;


/**
 * I start adapting from Udacity's Pets.
 * Displays list of habits that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity {
    Cursor cursor;
    final static int[] habitId = {R.string.habit0, 6, R.string.habit1, 7, R.string.habit2, 8, R.string.habit3, 9, R.string.habit4, 10, R.string.habit5, 11, R.string.habit6, 12, R.string.habit7, 14, R.string.habit8, 15, R.string.habit9, 16, R.string.habit10, 18, R.string.habit11, 20};

    /**
     * Database helper that will provide us access to the database
     */
    private HabitDbHelper mDbHelper;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM habits"
        // to get a Cursor that contains all rows from the habits table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + HabitEntry.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            n = 1;
        }

        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();

        if (n < 1) {
            n = 1;
            insertHabit();
        }
        displayDatabaseInfo();
    }

    public Cursor querryAllHabits(){
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT,
                HabitEntry.COLUMN_HABIT_SCHEDULE};

        // Perform a query on the habits table
         cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
return cursor;
    }
    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the habits database.
     */
    private void displayDatabaseInfo() {

       querryAllHabits();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " activities.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT + " - " +
                    HabitEntry.COLUMN_HABIT_SCHEDULE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT);
            int scheduleColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_SCHEDULE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentHabit = cursor.getString(habitColumnIndex);
                int currentSchedule = cursor.getInt(scheduleColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentHabit + " - " +
                        currentSchedule + ":00"));

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded habit data into the database. For debugging purposes only.
     */
    private void insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's habit attributes are the values.
        ContentValues values = new ContentValues();
        for (int i = 0; i < habitId.length; i += 2) {
            values.put(HabitEntry.COLUMN_HABIT, getResources().getString(habitId[i]));
            values.put(HabitEntry.COLUMN_HABIT_SCHEDULE, habitId[i + 1]);

            db.insert(HabitEntry.TABLE_NAME, null, values);

        }

    }



}
