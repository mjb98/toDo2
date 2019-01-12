package com.example.mjb.todo.database;

public class TaskdbSchema {
    public static final String NAME = "tasks.db";
    public static final int VERSION = 1;

    public static final class TaskTable {
        public static final String NAME = "task";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String isDone = "isdone";
            public static final String ownerUser = "owneruser";
        }
    }
    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Cols {
            public static final String userName = "username";
            public static final String passWord = "password";
        }
    }

}