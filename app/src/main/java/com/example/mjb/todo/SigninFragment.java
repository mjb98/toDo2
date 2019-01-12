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
public class SigninFragment extends Fragment {


    EditText usernameTextView;
    EditText passwordTextView;
    Button logInButton;
    TextView DHAA;
    TextView errorTextView;

    public static SigninFragment newInstance( ){
        SigninFragment signinFragment = new SigninFragment();
        return signinFragment;
    }

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        usernameTextView = view.findViewById(R.id.username_textiew);
        passwordTextView = view.findViewById(R.id.password_textiew);
        logInButton = view.findViewById(R.id.login_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user;
                try {
                    user = Userlab.getInstance(getContext()).getUser(usernameTextView.getText().toString(),passwordTextView.getText().toString());
                    if(user != null){
                        Intent intent = ViewPagerActivity.newIntent(getActivity(),user.getUserName());
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        errorTextView.setText("invalid inputs");
                    }
                }
                catch (Exception a){
                    errorTextView.setText("invalid inputs");
                }








            }
        });
        DHAA = view.findViewById(R.id.signin_dohaanacc_textview);
        DHAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        errorTextView = view.findViewById(R.id.singin_error_textview);
        return view;
    }

}
