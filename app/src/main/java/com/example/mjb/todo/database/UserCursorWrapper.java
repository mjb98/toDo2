package com.example.mjb.todo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.User;

import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public User getUser() {
        String username = getString(getColumnIndex(TaskdbSchema.UserTable.Cols.userName));
        String password = getString(getColumnIndex(TaskdbSchema.UserTable.Cols.passWord));
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        return user;

    }
}
