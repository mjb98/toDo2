package com.example.mjb.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {
    public TaskBaseHelper( Context context) {
        super(context, TaskdbSchema.NAME, null, TaskdbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TaskdbSchema.UserTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TaskdbSchema.UserTable.Cols.userName + ", " +
                TaskdbSchema.UserTable.Cols.passWord +
                ")"
        );



         String foreign_key = new String("FOREIGN KEY");
        db.execSQL("create table " + TaskdbSchema.TaskTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TaskdbSchema.TaskTable.Cols.UUID + ", " +
                TaskdbSchema.TaskTable.Cols.isDone + ", " +
                TaskdbSchema.TaskTable.Cols.DESCRIPTION + ", " +
                TaskdbSchema.TaskTable.Cols.DATE + ", " +
                TaskdbSchema.TaskTable.Cols.ownerUser + ", " +
                foreign_key+"("+TaskdbSchema.TaskTable.Cols.ownerUser+")" + " REFERENCES "+ TaskdbSchema.UserTable.NAME+"("+TaskdbSchema.UserTable.Cols.userName+")"+
                ")  "





        );


        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskdbSchema.UserTable.Cols.userName,"mamad");
        contentValues.put(TaskdbSchema.UserTable.Cols.passWord,"212");
        db.insert(TaskdbSchema.UserTable.NAME,null,contentValues);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
