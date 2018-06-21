package com.barrios.steve.userregistration;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.barrios.steve.userregistration.Fragments.UserEmailFragment;
import com.barrios.steve.userregistration.Model.UserRegistration;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private UserRegistration mUserRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserRegistration = new UserRegistration();

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null)
        {
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, UserEmailFragment.newInstance(mUserRegistration)).commit();
        }
    }
}
