package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

public class UserInterestFragment extends Fragment implements View.OnClickListener {

    private static final String USER = "user";
    private Button mBackButton, mNextButton;
    private Spinner mGenderInterestSpinner, mMinAgeSpinner, mMaxAgeSpinner;
    private String[] minage, maxage;
    private ArrayAdapter<String> mAdapter;
    private UserRegistration mUserRegistration;
    private boolean once;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minage = setuparray();
        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, minage);

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

        View v = inflater.inflate(R.layout.userinterestfragment, container, false);

        mNextButton = v.findViewById(R.id.user_interest_fragment_next_button);
        mNextButton.setOnClickListener(this);

        mBackButton = v.findViewById(R.id.user_interest_fragment_back_button);
        mBackButton.setOnClickListener(this);

        mGenderInterestSpinner = v.findViewById(R.id.user_interest_fragment_gender_interest_spinner);

        mMinAgeSpinner = v.findViewById(R.id.user_interest_fragment_gender_minimum_age_spinner);
        mMinAgeSpinner.setAdapter(mAdapter);
        mMaxAgeSpinner = v.findViewById(R.id.user_interest_fragment_gender_maximum_age_spinner);
        mMaxAgeSpinner.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.user_interest_fragment_next_button:

                if(validate() && once)
                {
                    once = false;
                    mUserRegistration.setGenderInterest(mGenderInterestSpinner.getSelectedItem().toString());
                    mUserRegistration.setMinage(mMinAgeSpinner.getSelectedItem().toString());
                    mUserRegistration.setMaxage(mMaxAgeSpinner.getSelectedItem().toString());
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, UserInfo3Fragment.newInstances(mUserRegistration))
                            .addToBackStack(null).commit();
                }

                break;
                default:
                    getFragmentManager().popBackStack();
                    break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        once = true;
    }

    private String[] setuparray()
    {
        String[] array = new String[82];

        for(int i = 0; i < 82; i++)
        {
            array[i] = String.format("%d", i + 18);
        }

        return array;
    }

    private boolean validate()
    {
        boolean valid = true;

        if(mGenderInterestSpinner.getSelectedItem().toString().equalsIgnoreCase("select"))
        {
            ((TextView)mGenderInterestSpinner.getSelectedView()).setError("Required");
            valid = false;
        }
        if(mMinAgeSpinner.getSelectedItemPosition() > mMaxAgeSpinner.getSelectedItemPosition())
        {
            ((TextView)mMinAgeSpinner.getSelectedView()).setError("Invalid Selection");
            valid = false;
        }
        if(mMaxAgeSpinner.getSelectedItemPosition() < mMinAgeSpinner.getSelectedItemPosition())
        {
            ((TextView)mMaxAgeSpinner.getSelectedView()).setError("Invalid Selection");
            valid = false;
        }

        return valid;
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {

        Fragment fragment = new UserInterestFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
