package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.AddedInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Calendar calendar;


    public DatePickerFragment newInstence(int year, int month, int day){
        DatePickerFragment datePickerFragment = new DatePickerFragment();


        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);

        datePickerFragment.setArguments(args);

        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int dayOfMonth;


        try{
            MainActivity mainActivity = (MainActivity) getActivity();
            calendar = mainActivity.getPresenter().getCalendar();
        }catch (Exception ignore){}



        try{

            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            dayOfMonth = getArguments().getInt("day");

        }catch (Exception ignore){

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        }


        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            Toast.makeText(getActivity(), "Niepoprawna data", Toast.LENGTH_SHORT).show();
        }else {
            try {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getPresenter().setCalendar(calendar);
            } catch (Exception ignore) {}
        }
    }
}
