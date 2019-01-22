package com.example.mjb.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;
import com.example.mjb.todo.models.Userlab;
import com.example.mjb.todo.utils.PictureUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditTaskFragment extends DialogFragment {

    public static final String TASK_ID = "task_id";
    public static final String ISFORADD = "isforadd";
    public static final String USERNAME = "username";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 323;
    public static final int PICK_IMAGE_REQUEST = 33;
    String strDateFormat1 = "yyyy-MM-dd";
    String strDateFormat = "hh:mm:ss ";
    EditText descriptionEditText;
    TextView dateTextView, timeTextview;
    Button mButton;
    CheckBox mCheckBox;
    CircleImageView mImageView;
    ImageButton camerabutton;
    private Task mTask;
    Uri imageUri;
    private File mPhotoFile;

    public static EditTaskFragment newInstance(String username, Boolean forADD) {

        Bundle args = new Bundle();
        args.putBoolean(ISFORADD, forADD);
        args.putCharSequence(USERNAME, username);
        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditTaskFragment newInstance(Long taskID, Boolean forADD) {

        Bundle args = new Bundle();
        args.putSerializable(TASK_ID, taskID);
        args.putBoolean(ISFORADD, forADD);

        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialouge__edit_task, container, false);
        descriptionEditText = view.findViewById(R.id.edittask_description);
        dateTextView = view.findViewById(R.id.Edittask_date);
        timeTextview = view.findViewById(R.id.edittask_time);
        mButton = view.findViewById(R.id.edittask_button);
        mCheckBox = view.findViewById(R.id.edittask_checkBox);
        mImageView = view.findViewById(R.id.task_imageview);
        camerabutton = view.findViewById(R.id.camerabutton);


        final boolean isforadd = getArguments().getBoolean(ISFORADD);
        if (isforadd) {
            mButton.setText(R.string.add);
            mTask = new Task(Userlab.getInstance(getActivity()).getUser(getArguments().getCharSequence(USERNAME).toString()));
        } else
            mTask = Tasklab.getInstance().getTask((Long) getArguments().getSerializable(TASK_ID));
        descriptionEditText.setText(mTask.getDescription() == null ? "" : mTask.getDescription());
        dateTextView.setText(new SimpleDateFormat(strDateFormat1).format(mTask.getDate()));
        timeTextview.setText(new SimpleDateFormat(strDateFormat).format(mTask.getDate()));
        mCheckBox.setChecked(mTask.getMdone());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setDescription(descriptionEditText.getText().toString());
                mTask.setMdone(mCheckBox.isChecked());
                if (isforadd) {
                    Tasklab.getInstance().addTask(mTask);
                } else
                    Tasklab.getInstance().updateTask(mTask);
                TaskListFragment taskListFragment = ((TaskListFragment) getTargetFragment());
                taskListFragment.updateUI();
                getDialog().dismiss();


            }
        });

        timeTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTextview.setText(selectedHour + ":" + selectedMinute);
                        Date date = mTask.getDate();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        date = calendar.getTime();
                        mTask.setDate(date);
                        setTime(mTask.getDate());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final DatePicker picker = new DatePicker(getActivity());
                picker.setCalendarViewShown(false);

                builder.setTitle("task date");
                builder.setView(picker);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(mTask.getDate());
                        int year = picker.getYear();
                        int month = picker.getMonth();
                        int day = picker.getDayOfMonth();
                        calendar.set(year, month, day);
                        mTask.setDate(calendar.getTime());
                        setDate(calendar.getTime());


                    }
                });

                builder.show();

            }
        });
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose way");
                builder.setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        imageUri = Uri.fromFile(mPhotoFile);
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                    }
                });
                builder.setNeutralButton("choose from gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                    }
                });
                builder.show();
            }
        });
        mPhotoFile = Tasklab.getInstance().getPhotoFile(mTask);



        return view;
    }

    private void setTime(Date date) {
        String strDateFormat = "hh:mm:ss ";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        timeTextview.setText(formattedDate);
    }

    private void setDate(Date date) {

        String strDateFormat1 = "yyyy-MM-dd";
        DateFormat dateFormat1 = new SimpleDateFormat(strDateFormat1);
        dateTextView.setText(dateFormat1.format(date));

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {


                mTask.setPhotoFileAdress(mPhotoFile.toString());
                updatePhotoView(mPhotoFile);




            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String[] projection = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null
            if(picturePath != null){
                mTask.setPhotoFileAdress(picturePath);
                updatePhotoView(new File(mTask.getPhotoFileAdress()));
            }
            cursor.close();

        }
    }
    private void updatePhotoView(File file) {
        if (file == null || !file.exists()) {

            mImageView.setImageResource(R.drawable.black);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    file.getPath(), 100,100);

            mImageView.setImageBitmap(bitmap);

        }
    }
}
