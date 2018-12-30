package com.example.mjb.todo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mjb.todo.models.Task;

import java.util.UUID;

public class TaskActivity extends SingleFragmentActivity {

    private static final String CRIME_CODE ="crimecode" ;
    public static final String ADD_KEY = "addkey";
    private boolean isAdding;
    private UUID taskId;
    @Override
    public Fragment createFragment() {
        isAdding = getIntent().getBooleanExtra(ADD_KEY,false);

        if(isAdding)
        return TaskFragment.newInstance(isAdding);
        else {
            taskId = (UUID) getIntent().getSerializableExtra(CRIME_CODE);
            return TaskFragment.newInstance(taskId);
        }
    }
    public static Intent newIntent(Context context,boolean isDone){
        Intent intent = new Intent(context,TaskActivity.class);
        intent.putExtra(ADD_KEY,isDone);
        return intent;
    }

    public static Intent newIntent(Context context, UUID crimeId){
        Intent intent = new Intent(context,TaskActivity.class);
        intent.putExtra(CRIME_CODE,crimeId);
        return intent;
    }


}