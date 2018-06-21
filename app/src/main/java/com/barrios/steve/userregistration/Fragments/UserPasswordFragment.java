package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

public class UserPasswordFragment extends Fragment implements View.OnClickListener{

    private static final String USER = "user";

    private Button mNextButton, mBackButton;
    private UserRegistration mUserRegistration;
    private EditText mPassword, mComfirmPassword;
    private boolean same, once;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            mUserRegistration = (UserRegistration) getArguments().getParcelable(USER);
            //Toast.makeText(getContext(), mUserRegistration.getEmail(), Toast.LENGTH_SHORT).show();
        }
        same = false;
        once = false;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.userpasswordfragment, container, false);

        mNextButton = v.findViewById(R.id.user_password_next_button);
        mNextButton.setOnClickListener(this);

        mBackButton = v.findViewById(R.id.user_password_back_button);
        mBackButton.setOnClickListener(this);

        mPassword = v.findViewById(R.id.user_password_fragment);
        mComfirmPassword = v.findViewById(R.id.user_confirm_password_fragment);

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mComfirmPassword.getText().toString().compareTo(s.toString()) != 0)
                {
                    mComfirmPassword.setError("Password doesn't match");
                    same = false;
                }
                else
                {
                    mComfirmPassword.setError(null);
                    same = true;
                }
            }
        });

        mComfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mPassword.getText().toString().compareTo(s.toString()) != 0)
                {
                    mComfirmPassword.setError("Password doesn't match");
                    same = false;
                }
                else
                {
                    same = true;
                }
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        once = false;
        mComfirmPassword.setError(null);
        //Toast.makeText(getContext(), mPassword.getText().toString() + " : " + mComfirmPassword.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.user_password_next_button:
                if(mPassword.getText().toString().toCharArray().length == 0)
                    mPassword.setError("Invalid password");
                if(mPassword.getText().toString().toCharArray().length < 8)
                    mPassword.setError("Password must be atleast 8 characters long");
                else if(same && !once)
                {
                    mUserRegistration.setPassword(mPassword.getText().toString());
                    once = true;
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, UserInfoFragment.newInstance(mUserRegistration))
                            .addToBackStack(null).commit();
                }

                break;
                default:
                    getFragmentManager().popBackStack();
        }

    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment = new UserPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
