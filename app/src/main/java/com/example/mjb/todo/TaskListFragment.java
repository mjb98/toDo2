package com.example.mjb.todo;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;
import com.example.mjb.todo.models.User;
import com.example.mjb.todo.models.Userlab;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */


public class TaskListFragment extends Fragment   {

    public static final int MODE_ALL = 0 ;
    public static final int MODE_DONE =1 ;
    public static final int MODE_UNDONE =2 ;

    private static final String ARG_TASK_ID = "idddddddd" ;
    public static final String SHOCRIME_TAG = "shocrime_tag";
    public static final String edittasdialoughe = "editextfragmnt";
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ImageView nothingImageView;
    private int position;
    private static User mUser;


    public static TaskListFragment newInstance(int Position,String username) {
        mUser = new User();
        mUser.setUserName(username);
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, Position);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasklistfragment,menu);
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
        setHasOptionsMenu(true);
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
        updateUI();
    }

    protected void updateUI() {
        List<Task> tasks = new ArrayList<Task>();
        System.out.println("posss  " + position);



        switch (position) {

            case MODE_ALL:
                try {
                    tasks = Tasklab.getInstance(getActivity()).getTaskList(mUser);
                    mTaskAdapter.notifyDataSetChanged();
                }catch (Exception a){

                }
                break;
            case 1:
                try {
                    tasks = Tasklab.getInstance(getActivity()).getDonelist(mUser);
                    mTaskAdapter.notifyDataSetChanged();

                }catch (Exception a){

                }
                break;
            case 2:
                try {
                    tasks = Tasklab.getInstance(getActivity()).getUnDonelist(mUser);
                    mTaskAdapter.notifyDataSetChanged();

                }catch (Exception a){

                }
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
      private TextView mEditButton;
      private Task mTask;






        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mDescriptionTextView = itemView.findViewById(R.id.list_item_task_description);
            mDateTextView = itemView.findViewById(R.id.list_item_task_date);
            mImageTextView = itemView.findViewById(R.id.image_textview);
            mEditButton = itemView.findViewById(R.id.item_edit_button);
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
a.printStackTrace();
           }
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                   Intent intent = TaskActivity.newIntent(getActivity(),mTask.getId());
//                   startActivity(intent);
                   ShowTaskFragment showTaskFragment = ShowTaskFragment.newInstance(mTask.getId());
                   showTaskFragment.show(getFragmentManager(), SHOCRIME_TAG);
               }
           });
           mEditButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(mTask.getId(),false);
                   editTaskFragment.setTargetFragment(TaskListFragment.this,12);
                   editTaskFragment.show(getFragmentManager(), edittasdialoughe);







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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.deleteTasks_menu_item:
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("All task will be delete");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Userlab.getInstance(getActivity()).deletaAllTasks(mUser);
                                updateUI();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cansle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;

            case R.id.addtask_menu_item:
                EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(mUser.getUserName(),true);
                editTaskFragment.setTargetFragment(TaskListFragment.this,0);
                editTaskFragment.show(getFragmentManager(),"32");



                default:return super.onOptionsItemSelected(item);
        }


        return true;
    }

}

