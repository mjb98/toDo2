package com.example.mjb.todo.models;

import android.os.Environment;

import com.example.mjb.todo.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.List;

public class Tasklab {
    private static Tasklab ourInstance;
    private TaskDao mTaskDao = (App.getApp()).getDaoSession().getTaskDao();



    public static Tasklab getInstance() {
        if (ourInstance == null)
            ourInstance = new Tasklab();
        return ourInstance;
    }


    public List<Task> getTaskList(User user) {
//        List<Task> tasks = new ArrayList<>();
//        TaskCursorWrapper cursor = queryTasks(TaskdbSchema.TaskTable.Cols.ownerUser +" = ? ",new String[]{user.getUserName()});
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                tasks.add(cursor.getTask());
//                cursor.moveToNext();
//            }
//        } finally {
//            cursor.close();
//        }
        QueryBuilder<Task> qb = mTaskDao.queryBuilder();
        qb.where(TaskDao.Properties.MOwnerUserName.eq(user.getUserName()));
        List<Task> tasks = qb.list();
        return tasks;



    }
    public List<Task> getDonelist(User user) {
//        List<Task> tasks = new ArrayList<>();
//        TaskCursorWrapper cursor = queryTasks(TaskdbSchema.TaskTable.Cols.isDone + " = "+1 + " AND " + TaskdbSchema.TaskTable.Cols.ownerUser +" = ? ",new String[]{user.getUserName()});
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                tasks.add(cursor.getTask());
//                cursor.moveToNext();
//            }
//        } finally {
//            cursor.close();
//        }
//        return tasks;
        QueryBuilder<Task> qb = mTaskDao.queryBuilder();
        qb.where(TaskDao.Properties.MOwnerUserName.eq(user.getUserName()),TaskDao.Properties.Mdone.eq(1));
        List<Task> tasks = qb.list();
        return tasks;




    }
    public List<Task> getUnDonelist(User user) {
        QueryBuilder<Task> qb = mTaskDao.queryBuilder();
        qb.where(TaskDao.Properties.MOwnerUserName.eq(user.getUserName()),TaskDao.Properties.Mdone.eq(0));
        List<Task> tasks = qb.list();
        return tasks;
    }




    public void addTask(Task task) {

        mTaskDao.insert(task);

    }


    public  Task getTask(Long id) {
       return mTaskDao.load(id);


    }

    public void deleteTask(Task task) {
       mTaskDao.delete(task);


    }



    public void updateTask(Task task) {
       mTaskDao.update(task);
    }
    public File getPhotoFile(Task task) {

        if(task.getPhotoFileAdress() != null){
            return new File(task.getPhotoFileAdress());
        }

        File filesDir = Environment.getExternalStorageDirectory();
        File photoFile = new File(filesDir, task.getPhotoName());

        return photoFile;
    }
}
