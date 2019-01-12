package com.example.mjb.todo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mjb.todo.database.TaskBaseHelper;
import com.example.mjb.todo.database.TaskCursorWrapper;
import com.example.mjb.todo.database.TaskdbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tasklab {
    private static Tasklab ourInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static Tasklab getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Tasklab(context);
        return ourInstance;
    }


    public List<Task> getTaskList(User user) {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryCrimes(TaskdbSchema.TaskTable.Cols.ownerUser +" = ? ",new String[]{user.getUserName()});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }
    public List<Task> getDonelist(User user) {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryCrimes(TaskdbSchema.TaskTable.Cols.isDone + " = "+1 + " AND " + TaskdbSchema.TaskTable.Cols.ownerUser +" = ? ",new String[]{user.getUserName()});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }
    public List<Task> getUnDonelist(User user) {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryCrimes(TaskdbSchema.TaskTable.Cols.isDone + " = "+0 + " AND " + TaskdbSchema.TaskTable.Cols.ownerUser +" = ? ",new String[]{user.getUserName()});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    private Tasklab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();


    }


    public void addTask(Task task) {

        ContentValues values = getContentValues(task);
        mDatabase.insert(TaskdbSchema.TaskTable.NAME, null, values);
        System.out.println("add task");

    }


    public  Task getTask(UUID id) {

        TaskCursorWrapper cursor = queryCrimes(
                TaskdbSchema.TaskTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    public void deleteTask(Task task) {
        String where = TaskdbSchema.TaskTable.Cols.UUID + "= ?";
        mDatabase.delete(TaskdbSchema.TaskTable.NAME, where, new String[]{task.getId().toString()});


    }

    public ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskdbSchema.TaskTable.Cols.UUID, task.getId().toString());
        values.put(TaskdbSchema.TaskTable.Cols.DESCRIPTION, task.getDescription());
        values.put(TaskdbSchema.TaskTable.Cols.DATE, task.getDate().getTime());
        values.put(TaskdbSchema.TaskTable.Cols.isDone, task.getMdone() ? 1 : 0);
        values.put(TaskdbSchema.TaskTable.Cols.ownerUser, task.getOwnerUserName());

        return values;
    }

    private TaskCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskdbSchema.TaskTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new TaskCursorWrapper(cursor);


    }
    public void updateTask(Task task) {
        ContentValues values = getContentValues(task);
        String whereClause = TaskdbSchema.TaskTable.Cols.UUID + " = ? ";
        mDatabase.update(TaskdbSchema.TaskTable.NAME, values,
                whereClause, new String[]{task.getId().toString()});
    }
}
