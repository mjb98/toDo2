package com.example.mjb.todo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.User;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskdbSchema.TaskTable.Cols.UUID));
        String Description = getString(getColumnIndex(TaskdbSchema.TaskTable.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(TaskdbSchema.TaskTable.Cols.DATE));
        int isDone = getInt(getColumnIndex(TaskdbSchema.TaskTable.Cols.isDone));
        Task task = new Task(new User(),UUID.fromString(uuidString));
        task.setDescription(Description);
        task.setDate(new Date(date));
        task.setMdone(isDone == 1);
        return task;
    }
}
