package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.barrios.steve.userregistration.DialogToFragmentListener;
import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserInfo2Fragment extends Fragment implements View.OnClickListener, DialogToFragmentListener {

    private static final String USER = "user";

    private Button mNextButton;
    private Button mBackButton;
    private Spinner mGenderSpinner;
    private EditText mBirthday;
    private UserInfo2Fragment mUserInfo2Fragment;
    private UserRegistration mUserRegistration;
    private GregorianCalendar mGregorianCalendar;
    private boolean once;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            mUserRegistration = (UserRegistration) getArguments().getParcelable(USER);
            //Toast.makeText(getContext(), mUserRegistration.getEmail(), Toast.LENGTH_SHORT).show();
        }

        once = true;
        mUserInfo2Fragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.userinfo2fragment, container, false);

        mNextButton = v.findViewById(R.id.user_info_2_fragment_next_button);
        mBackButton = v.findViewById(R.id.user_info_2_fragment_back_button);

        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        mBirthday = v.findViewById(R.id.user_info_2_fragment_birthday_edittext);
        mBirthday.setKeyListener(null);
        mBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    BirthdayPickerFragment fragment = new BirthdayPickerFragment();
                    fragment.setTargetFragment(mUserInfo2Fragment, 0);
                    fragment.show(getFragmentManager(), null);
                }

            }
        });

        mGenderSpinner = v.findViewById(R.id.user_info_2_fragment_gender_spinner);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.user_info_2_fragment_next_button:
                if(validate() && once)
                {
                    once = false;

                    mUserRegistration.setBirthday(mBirthday.getText().toString());
                    mUserRegistration.setGender(mGenderSpinner.getSelectedItem().toString());

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, UserInterestFragment.newInstance(mUserRegistration))
                            .addToBackStack(null).commit();
                }

                break;
            default:
                getFragmentManager().popBackStack();
                break;

        }
    }

    @Override
    public void OnPickedListener(GregorianCalendar s) {
        mBirthday.setFocusable(false);
        mBirthday.setFocusableInTouchMode(false);
        mBirthday.setFocusable(true);
        mBirthday.setFocusableInTouchMode(true);

        if(s != null)
        {
            mGregorianCalendar = s;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            dateFormat.setCalendar(s);

            mBirthday.setText(dateFormat.format(s.getTime()));

            if(mBirthday.getError() != null)
                mBirthday.setError(null);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        once = true;
    }

    private boolean validate()
    {
        boolean valid = true;
        if(mBirthday.getText().toString().toCharArray().length == 0)
        {
            mBirthday.setError("Required");
            valid = false;
        }
        if(mGenderSpinner.getSelectedItem().toString().equalsIgnoreCase("select"))
        {
            ((TextView)mGenderSpinner.getSelectedView()).setError("Required");
            valid = false;
        }
        if(mGregorianCalendar != null && new GregorianCalendar().get(Calendar.YEAR) - mGregorianCalendar.get(Calendar.YEAR) < 18)
        {
            mBirthday.setError("Must be 18 or older");
            valid = false;
        }

        return valid;
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment = new UserInfo2Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
