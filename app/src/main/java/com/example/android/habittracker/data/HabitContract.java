package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Habits app.
 */
public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    /**
     * Inner class that defines constant values for the Habit database table.
     * Each entry in the table represents a single activity.
     */
    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table for habits
         * Unique ID number for the habit
         */
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT = "habit";
        public final static String COLUMN_HABIT_SCHEDULE = "schedule";

    }

}

