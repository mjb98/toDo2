package com.example.mjb.todo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    private static final String ARG_TASK_ID = "idddddddd" ;
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ImageView nothingImageView;
    private int position;


    public static TaskListFragment newInstance(int Position) {

        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, Position);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    public TaskListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_TASK_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycller_view);
        nothingImageView = view.findViewById(R.id.nothing_imageview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
        System.out.println("ey jaaaaaan");
        updateUI();
    }

    private void updateUI() {
        List<Task> tasks = new ArrayList<Task>();
        System.out.println("posss  " + position);
        switch (position) {

            case 0:
                tasks = Tasklab.getInstance().getTaskList();
                break;
            case 1:
                tasks = Tasklab.getInstance().getDonelist();
                break;
            case 2:
                tasks = Tasklab.getInstance().getUnDonelist();
                break;

        }
        if(mTaskAdapter == null) {


            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        }
        else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
        nothingImageView.setVisibility(tasks.size() == 0 ? View.VISIBLE : View.GONE);
    }
    private class TaskHolder extends RecyclerView.ViewHolder{

      private TextView mDescriptionTextView;
      private TextView mDateTextView;
      private TextView mImageTextView;
      private Task mTask;






        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mDescriptionTextView = itemView.findViewById(R.id.list_item_task_description);
            mDateTextView = itemView.findViewById(R.id.list_item_task_date);
            mImageTextView = itemView.findViewById(R.id.image_textview);
        }
        public void bind(Task task) {
            mTask = task;
            mDateTextView.setText(task.getDate().toString());
           try{
               String description = task.getDescription();

                   mDescriptionTextView.setText(description);
                   Character letter = description.charAt(0);
                 mImageTextView.setText(letter.toString());

           }catch (Exception a){
               mDescriptionTextView.setText("");
               mImageTextView.setText("");

           }
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = TaskActivity.newIntent(getActivity(),mTask.getId());
                   startActivity(intent);
               }
           });




        }
    }
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{

        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_task,viewGroup,false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder taskHolder, int position) {
            Task task = mTasks.get(position);
            taskHolder.bind(task);

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

}
