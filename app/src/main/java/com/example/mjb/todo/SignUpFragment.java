package com.example.mjb.todo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mjb.todo.models.User;
import com.example.mjb.todo.models.Userlab;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    EditText username;
    EditText password;
    EditText confrimPassword;
    Button signUp;
    TextView ahaa;
    TextView error;

    public static SignUpFragment newInstance( ){
        SignUpFragment signUpFragment = new SignUpFragment();
        return signUpFragment;
    }



    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        username = view.findViewById(R.id.signup_username_textiew);
        password = view.findViewById(R.id.signup_password_textiew);
        confrimPassword = view.findViewById(R.id.signup_confrimpassword_textiew);
        signUp = view.findViewById(R.id.signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") || username.getText().toString().contains(" ") || password.getText().toString().equals("") || password.getText().toString().contains(" ")) {
                    error.setText("invalid inputs");
                    return;
                }
                if(Userlab.getInstance(getContext()).getUser(username.getText().toString()) != null){
                    error.setText("already exist "+ username.getText().toString());
                    return;
                }
                if(!password.getText().toString().equals(confrimPassword.getText().toString())){
                    error.setText("enter same passwords");
                    return;
                }
                User user = new User();
                user.setUserName(username.getText().toString());
                user.setPassWord(password.getText().toString());
                Userlab.getInstance(getContext()).addUser(user);
                Intent intent = ViewPagerActivity.newIntent(getActivity(),user.getUserName());
                startActivity(intent);
                getActivity().finish();

            }
        });
        ahaa = view.findViewById(R.id.singnup_alhaacc_textview);
        ahaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SigninActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        error = view.findViewById(R.id.singnup_error_textview);


        return view;
    }

}
