package com.example.mjb.todo.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Tasklab {
    private static final Tasklab ourInstance = new Tasklab();
    private LinkedHashMap<UUID,Task> mtasks;


    public static Tasklab getInstance() {
        return ourInstance;
    }

    public  List<Task> getTaskList(){
        List<Task> list = new ArrayList<Task>(ourInstance.mtasks.values());
        return list;
    }

    private Tasklab() {
        mtasks = new LinkedHashMap<UUID,Task>();
        for (int i = 0; i <10 ; i++) {

            Task task = new Task();
            task.setDescription(i%2 ==0 ?"ali":"mamad");
            task.setMdone(i%2 == 0);
            mtasks.put(task.getId(),task);
        }

    }
    public List<Task> getDonelist(){
        List<Task> doneTask = new ArrayList<Task>();
        for(Task task :ourInstance.mtasks.values()){
            if(task.getMdone() == true){
                doneTask.add(task);
            }

        }
        return doneTask;
    }
    public List<Task> getUnDonelist(){
        List<Task> UndoneTask = new ArrayList<Task>();
        for(Task task :ourInstance.mtasks.values()){
            if(task.getMdone() != true){
                UndoneTask.add(task);
            }

        }
        return UndoneTask;
    }


}
