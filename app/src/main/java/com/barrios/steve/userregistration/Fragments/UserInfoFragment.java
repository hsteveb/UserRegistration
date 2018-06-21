package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

public class UserInfoFragment extends Fragment implements View.OnClickListener {

    private static final String USER = "user";
    private boolean allvalid, once;
    private Button mNextButton, mBackButton;
    private EditText mFirstName, mMiddleName, mLastName, mZipcode;
    private Spinner mHeightFt, mHeightIn;
    private UserRegistration mUserRegistration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mUserRegistration = getArguments().getParcelable(USER);
        }
        allvalid = false;
        once = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.userinfofragment, container, false);

        mNextButton = v.findViewById(R.id.user_info_fragment_next_button);
        mNextButton.setOnClickListener(this);

        mBackButton = v.findViewById(R.id.user_info_fragment_back_button);
        mBackButton.setOnClickListener(this);

        mFirstName = v.findViewById(R.id.user_info_fragment_first_name);
        mMiddleName = v.findViewById(R.id.user_info_fragment_middle_name);
        mLastName = v.findViewById(R.id.user_info_fragment_last_name);
        mZipcode = v.findViewById(R.id.user_info_fragment_zipcode);
        mHeightFt = v.findViewById(R.id.user_info_fragment_height_ft);
        mHeightIn = v.findViewById(R.id.user_info_fragment_height_inches);

        mZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().toCharArray().length < 5)
                {
                    mZipcode.setError("Invalid zipcode");
                    allvalid = false;
                }
                else
                    allvalid = true;

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.user_info_fragment_next_button:
                if(validate() && allvalid && once) {

                    once = false;

                    mUserRegistration.setFirstName(mFirstName.getText().toString());
                    mUserRegistration.setMiddlename(mMiddleName.getText().toString());
                    mUserRegistration.setLastName(mLastName.getText().toString());
                    mUserRegistration.setZipcode(mZipcode.getText().toString());
                    mUserRegistration.setHeightFt(mHeightFt.getSelectedItem().toString());
                    mUserRegistration.setHeightIn(mHeightIn.getSelectedItem().toString());

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.anim.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, UserInfo2Fragment.newInstance(mUserRegistration))
                            .addToBackStack(null).commit();
                }

                break;
                default:
                    getFragmentManager().popBackStack();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        once = true;
    }

    private boolean validate()
    {
        boolean allvalid = true;
        if(mFirstName.getText().toString().toCharArray().length == 0)
        {
            mFirstName.setError("Required");
            allvalid = false;
        }
        if(mMiddleName.getText().toString().toCharArray().length == 0)
        {
            mMiddleName.setError("Required");
            allvalid = false;
        }
        if(mLastName.getText().toString().toCharArray().length == 0)
        {
            mLastName.setError("Required");
            allvalid = false;
        }
        if(mZipcode.getText().toString().toCharArray().length == 0)
        {
            mZipcode.setError("Required");
            allvalid = false;
        }
        if(mHeightFt.getSelectedItem().toString().equalsIgnoreCase("0\'"))
        {
            ((TextView)mHeightFt.getSelectedView()).setError("Required");
            allvalid = false;
        }


        return allvalid;
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
