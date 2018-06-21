package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

public class UserInfo3Fragment extends Fragment implements View.OnClickListener {

    private static final String USER = "user";

    private Button mNextButton, mBackButton;
    private Spinner mRaceSpinner, mReligionSpinner;
    private UserRegistration mUserRegistration;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.userinfo3fragment, container, false);

        mNextButton = v.findViewById(R.id.user_info_3_fragment_next_button);
        mBackButton = v.findViewById(R.id.user_info_3_fragment_back_button);

        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        mRaceSpinner = v.findViewById(R.id.user_info_3_fragment_race_spinner);
        mReligionSpinner = v.findViewById(R.id.user_info_3_fragment_religion_spinner);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.user_info_3_fragment_next_button:

                if(once)
                {
                    once = false;

                    if(mRaceSpinner.getSelectedItem().toString().equalsIgnoreCase("select"))
                        mUserRegistration.setRace("");
                    else
                        mUserRegistration.setRace(mRaceSpinner.getSelectedItem().toString());
                    if(mReligionSpinner.getSelectedItem().toString().equalsIgnoreCase("select"))
                        mUserRegistration.setReligion("");
                    else
                        mUserRegistration.setReligion(mReligionSpinner.getSelectedItem().toString());

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, UserPictureFragment.newInstace(mUserRegistration))
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

    public static Fragment newInstances(UserRegistration userRegistration)
    {

        Fragment fragment = new UserInfo3Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
