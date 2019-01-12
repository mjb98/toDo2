package com.example.mjb.todo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;
import com.example.mjb.todo.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    private static final String ARG_TASK_ID = "addtaskida";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    Button addButton;
     Button editButton;
     Button doneButton;
     Button deleteButton;
     TextView mDateTextview;
     TextView mTimeTextview;
     EditText mDescriptionTextView;
     Task mTask;
     private static String mUsername;
     static Boolean isAdding;


    public static TaskFragment newInstance(Boolean isForAdd,String username){
        TaskFragment taskFragment = new TaskFragment();
        mUsername = username;
        isAdding = isForAdd;
         return taskFragment;

    }

    public static TaskFragment newInstance(UUID taskId){
        TaskFragment taskFragment = new TaskFragment();
        isAdding = false;
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        taskFragment.setArguments(args);
        return taskFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isAdding)
        mTask = Tasklab.getInstance(getActivity()).getTask((UUID) getArguments().getSerializable(ARG_TASK_ID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        addButton = view.findViewById(R.id.add_button);
        editButton = view.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setOwnerUserName(mUsername);
                mTask.setDescription(mDescriptionTextView.getText().toString());
               Tasklab.getInstance(getActivity()).updateTask(mTask);
                getActivity().finish();
            }
        });

        doneButton = view.findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setMdone(!mTask.getMdone());
                updateEditButtonText();
            }


        });
        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this task");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tasklab.getInstance(getActivity()).deleteTask(mTask);
                        getActivity().finish();

                    }
                });
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setNeutralButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });
        mDateTextview = view.findViewById(R.id.date_text);
        mDateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mTask.getDate());
                dialog.setTargetFragment(TaskFragment.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);

            }
        });
        mTimeTextview = view.findViewById(R.id.time_text);
        mTimeTextview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTimeTextview.setText( selectedHour + ":" + selectedMinute);
                        Date date = mTask.getDate();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                        calendar.set(Calendar.MINUTE,selectedMinute);
                        date = calendar.getTime();
                        mTask.setDate(date);
                        setTime(mTask.getDate());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        mDescriptionTextView = view.findViewById(R.id.descriptionEdittext);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setDescription(mDescriptionTextView.getText().toString());
               Tasklab.getInstance(getActivity()).addTask(mTask);
                getActivity().finish();
            }
        });

        if(isAdding){
            mTask = new Task(new User());
            mTask.setOwnerUserName(mUsername);
            editButton.setEnabled(false);
            doneButton.setEnabled(false);
            deleteButton.setEnabled(false);
            Date date = new Date();
            mTask.setDate(date);
            setDate(date);
            setTime(date);

        }else {
            addButton.setVisibility(View.GONE);
            mDescriptionTextView.setText(mTask.getDescription());
            setDate(mTask.getDate());
            setTime(mTask.getDate());
            updateEditButtonText();



        }



        return view;
    }

    private void updateEditButtonText() {
        if(mTask.getMdone()){
            doneButton.setText(R.string.done);
            doneButton.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.checkbox_on_background,0,0,0);

        }else {
            doneButton.setText(R.string.undone);
            doneButton.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.checkbox_off_background,0,0,0);
        }
    }

    private void setDate(Date date){

        String strDateFormat1 = "yyyy-MM-dd";
        DateFormat dateFormat1 = new SimpleDateFormat(strDateFormat1);
        mDateTextview.setText(dateFormat1.format(date));


    }
    private void setTime(Date date){
        String strDateFormat = "hh:mm:ss ";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat. format(date);
        mTimeTextview.setText(formattedDate);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            setDate(mTask.getDate());
        }
    }
}
