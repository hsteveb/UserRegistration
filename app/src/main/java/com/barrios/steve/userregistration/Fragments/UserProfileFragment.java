package com.barrios.steve.userregistration.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

public class UserProfileFragment extends Fragment implements View.OnClickListener{

    private static final String USER = "user";
    private ImageView mImageView;
    private Button mNextButton, mBackButton;
    private TextView mNameTextView, mBirthdayTextView, mGenderTextView, mGenderInterestTextView, mAgeInterestTextView, mPasswordTextView, mReligionTextview, mRaceTextView, mHeightTextView;
    private UserRegistration mUserRegistration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            mUserRegistration = (UserRegistration) getArguments().getParcelable(USER);
            //Toast.makeText(getContext(), mUserRegistration.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.userprofilefragment, container, false);

        mImageView = v.findViewById(R.id.user_profile_image_view);
        mImageView.setImageBitmap(mUserRegistration.getBitmap());

        mNextButton = v.findViewById(R.id.user_profile_next_button);
        mNextButton.setOnClickListener(this);

        mBackButton = v.findViewById(R.id.user_profile_back_button);
        mBackButton.setOnClickListener(this);

        mNameTextView = v.findViewById(R.id.user_profile_fragment_name_text_view);
        mNameTextView.setText(String.format("%s %s %s", mUserRegistration.getFirstName(), mUserRegistration.getMiddlename(), mUserRegistration.getLastName()));

        mBirthdayTextView = v.findViewById(R.id.user_profile_fragment_birthday_text_view);
        mBirthdayTextView.setText("Birthday: " + mUserRegistration.getBirthday());

        mGenderTextView = v.findViewById(R.id.user_profile_fragment_gender_text_view);
        mGenderTextView.setText("Gender: " + mUserRegistration.getGender());

        mGenderInterestTextView = v.findViewById(R.id.user_profile_fragment_gender_interest_text_view);
        mGenderInterestTextView.setText("Gender: " + mUserRegistration.getGenderInterest());

        mAgeInterestTextView = v.findViewById(R.id.user_profile_fragment_age_interest_text_view);
        mAgeInterestTextView.setText("Age(min): " + mUserRegistration.getMinage() + "    Age(max): " + mUserRegistration.getMaxage());

        mPasswordTextView = v.findViewById(R.id.user_profile_fragment_password_text_view);
        mPasswordTextView.setText("Password: " + mUserRegistration.getPassword());

        mReligionTextview = v.findViewById(R.id.user_profile_fragment_religion_text_view);
        mReligionTextview.setText("Religion: " + mUserRegistration.getReligion());

        mRaceTextView = v.findViewById(R.id.user_profile_fragment_race_text_view);
        mRaceTextView.setText("Race: " + mUserRegistration.getRace());

        mHeightTextView = v.findViewById(R.id.user_profile_fragment_height_text_view);
        mHeightTextView.setText("Height: " + mUserRegistration.getHeightFt() + " " + mUserRegistration.getHeightIn());
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.user_profile_next_button:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, UserSendInfoFragment.newInstance(mUserRegistration))
                        .addToBackStack(null).commit();
                break;
                default:
                    getFragmentManager().popBackStack();
                    break;
        }
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }
}
