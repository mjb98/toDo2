package com.example.mjb.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class ShowTaskFragment extends DialogFragment {

    public static final String UUID_CODE = "UUID_CODE";
    TextView DescriptionTextView,DateTextView,TimeTextView;
    Button OKButton;
    ImageButton shareTaskButton;
    CheckBox mCheckBox;
    Task mTask;
    String strDateFormat1 = "yyyy-MM-dd";
    String strDateFormat = "hh:mm:ss ";

    public static ShowTaskFragment newInstance(UUID TaskID) {

        Bundle args = new Bundle();
        args.putSerializable(UUID_CODE,TaskID);
        ShowTaskFragment fragment = new ShowTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialouge__sho_task,container,false);
        DescriptionTextView = view.findViewById(R.id.edittask_description);
        DateTextView = view.findViewById(R.id.Edittask_date);
        TimeTextView = view.findViewById(R.id.edittask_time);
        shareTaskButton = view.findViewById(R.id.sharebutton);
        shareTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent();
                reportIntent.setAction(Intent.ACTION_SEND);
                reportIntent.setType("text/plain");
                reportIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.taskreport_report,mTask.getDescription(),mTask.getDate().toString(),mTask.getMdone().toString()));
                startActivity(Intent.createChooser(reportIntent,"Share This Task"));

            }
        });
        mCheckBox = view.findViewById(R.id.showtask_checkBox);
        mCheckBox.setClickable(false);
        OKButton = view.findViewById(R.id.shotask_ok_button);
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        mTask = Tasklab.getInstance(getActivity()).getTask((UUID) getArguments().getSerializable(UUID_CODE));
        DescriptionTextView.setText(mTask.getDescription());
        DateTextView.setText(new SimpleDateFormat(strDateFormat1).format(mTask.getDate()));
        TimeTextView.setText(new SimpleDateFormat(strDateFormat).format(mTask.getDate()));
        mCheckBox.setChecked(mTask.getMdone());



        return view;
    }



}
