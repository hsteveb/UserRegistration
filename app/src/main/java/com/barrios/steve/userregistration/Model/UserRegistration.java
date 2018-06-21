package com.barrios.steve.userregistration.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class UserRegistration implements Parcelable {

    private String heightFt, heightIn, zipcode, minage, maxage;
    private String firstName, middlename, lastName, email, password, race, religion, gender, genderInterest, birthday;
    private Bitmap mBitmap;

    public UserRegistration()
    {

    }

    private UserRegistration(Parcel in)
    {
        heightFt = in.readString();
        heightIn = in.readString();
        zipcode = in.readString();
        minage = in.readString();
        maxage = in.readString();
        firstName = in.readString();
        middlename = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        race = in.readString();
        religion = in.readString();
        gender = in.readString();
        genderInterest = in.readString();
        birthday = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(String heightFt) {
        this.heightFt = heightFt;
    }

    public String getHeightIn() {
        return heightIn;
    }

    public void setHeightIn(String heightIn) {
        this.heightIn = heightIn;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMinage() {
        return minage;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public String getMaxage() {
        return maxage;
    }

    public void setMaxage(String maxage) {
        this.maxage = maxage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderInterest() {
        return genderInterest;
    }

    public void setGenderInterest(String genderInterest) {
        this.genderInterest = genderInterest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(heightFt);
        dest.writeString(heightIn);
        dest.writeString(zipcode);
        dest.writeString(minage);
        dest.writeString(maxage);
        dest.writeString(firstName);
        dest.writeString(middlename);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(race);
        dest.writeString(religion);
        dest.writeString(gender);
        dest.writeString(genderInterest);
        dest.writeString(birthday);
        dest.writeParcelable(mBitmap, 0);
    }

    public static final Parcelable.Creator<UserRegistration> CREATOR = new Parcelable.Creator<UserRegistration>()
    {

        @Override
        public UserRegistration createFromParcel(Parcel source) {
            return new UserRegistration(source);
        }

        @Override
        public UserRegistration[] newArray(int size) {
            return new UserRegistration[size];
        }
    };
}
