package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.Detail;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Calendar calendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        try{
            DetailActivity detailActivity = (DetailActivity) getActivity();
            calendar = detailActivity.getPresenter().getCalendar();
        }catch (Exception ignore){}

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



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
            DetailActivity detailActivity = (DetailActivity) getActivity();
            detailActivity.getPresenter().setCalendar(calendar);
        }
    }
}
