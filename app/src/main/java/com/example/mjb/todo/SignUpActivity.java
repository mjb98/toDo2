package com.example.mjb.todo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignUpActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,SignUpActivity.class);
        return intent;
    }
    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}