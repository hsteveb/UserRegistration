package com.barrios.steve.userregistration.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.widget.DatePicker;

import com.barrios.steve.userregistration.DialogToFragmentListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BirthdayPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        ((UserInfo2Fragment)getTargetFragment()).OnPickedListener(new GregorianCalendar(year, month, dayOfMonth));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((UserInfo2Fragment)getTargetFragment()).OnPickedListener( null);
    }
}
