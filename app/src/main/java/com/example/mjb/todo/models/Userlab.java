package com.example.mjb.todo.models;

import android.content.ContentValues;
import android.content.Context;

import com.example.mjb.todo.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class Userlab {
    private static Userlab ourInstance;
    private UserDao mUserDao = (App.getApp()).getDaoSession().getUserDao();
    private TaskDao mTaskDao = (App.getApp()).getDaoSession().getTaskDao();
    private Userlab(Context context) {


    }
    public static Userlab getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Userlab(context);
        return ourInstance;
    }

    public  User getUser(String username,String password) {
        QueryBuilder<User> qb = mUserDao.queryBuilder();
        qb.where(UserDao.Properties.UserName.eq(username),UserDao.Properties.PassWord.eq(password));
        User user = qb.uniqueOrThrow();
        return user;
    }
    public  User getUser(String username) {

        QueryBuilder<User> qb = mUserDao.queryBuilder();
        qb.where(UserDao.Properties.UserName.eq(username));
        User user = qb.unique();
        return user;

    }
    public void addUser(User user) {
        mUserDao.insert(user);
    }


    public void deletaAllTasks(User user){

        QueryBuilder<Task> qb = mTaskDao.queryBuilder();
        qb.and(TaskDao.Properties.UserId.eq(user.getID()),TaskDao.Properties.Mdone.eq(true));
        List<Task> tasks = qb.list();
        for (Task task : tasks){
            mTaskDao.delete(task);
        }


    }
}
