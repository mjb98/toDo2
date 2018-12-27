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
            task.setDescription("ali");
            mtasks.put(task.getId(),task);
        }



    }


}
