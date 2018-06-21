package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

import java.io.Serializable;

public class UserEmailFragment extends Fragment implements View.OnClickListener{

    private static final String USER = "user";
    private static final String TAG = "UserEmailFragment";

    private boolean once;
    private Button mNextButton;
    private EditText mEditText;
    private UserRegistration mUserRegistration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        once = true;
        if(getArguments() != null)
        {
            //Log.d(TAG, "testing this shit");
            mUserRegistration = getArguments().getParcelable(USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.useremailfragment, container, false);

        mNextButton = v.findViewById(R.id.user_email_fragment_next_button);
        mNextButton.setOnClickListener(this);

        mEditText = v.findViewById(R.id.user_email_fragment_email_edittext);


        return v;
    }

    @Override
    public void onClick(View v) {

        if(once && Patterns.EMAIL_ADDRESS.matcher(mEditText.getText().toString()).matches() && mEditText.getText().toString().toCharArray().length <= 320)
        {
            once = false;
            mUserRegistration.setEmail(mEditText.getText().toString());
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, UserPasswordFragment.newInstance(mUserRegistration))
                    .addToBackStack(null).commit();
        }

        else
        {
            mEditText.setError("Invalid email");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        once = true;
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment= new UserEmailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);
        return fragment;
    }
}
