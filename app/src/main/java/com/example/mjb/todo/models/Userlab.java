package com.example.mjb.todo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mjb.todo.database.TaskBaseHelper;
import com.example.mjb.todo.database.TaskCursorWrapper;
import com.example.mjb.todo.database.TaskdbSchema;
import com.example.mjb.todo.database.UserCursorWrapper;

import java.util.UUID;

public class Userlab {
    private static Userlab ourInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private Userlab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();

    }
    public static Userlab getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Userlab(context);
        return ourInstance;
    }
    private UserCursorWrapper queryCrimes(String whereClause,String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskdbSchema.UserTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new UserCursorWrapper(cursor);


    }
    public  User getUser(String username,String password) {

        UserCursorWrapper cursor = queryCrimes(
                TaskdbSchema.UserTable.Cols.userName + " = ?" +" AND " + TaskdbSchema.UserTable.Cols.passWord + " = ?",new String[]{username,password}

        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }
    public  User getUser(String username) {

        UserCursorWrapper cursor = queryCrimes(
                TaskdbSchema.UserTable.Cols.userName + " = ?",new String[]{username}

        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }

    }
    public void addUser(User user) {

        ContentValues values = getContentValues(user);
        mDatabase.insert(TaskdbSchema.UserTable.NAME, null, values);
    }

    public ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskdbSchema.UserTable.Cols.userName,user.getUserName());
        values.put(TaskdbSchema.UserTable.Cols.passWord,user.getPassWord());
        return values;
    }
}
