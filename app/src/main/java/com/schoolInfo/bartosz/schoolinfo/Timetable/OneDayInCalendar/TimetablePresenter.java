package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar;

import android.util.Log;
import android.widget.Space;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;


public class TimetablePresenter extends MvpBasePresenter<TimetableView> {
    List<String> subjects, classNumb, listTime;


    public TimetablePresenter() {
    }

    public void setDate(List<String> subjects, List<String> classNumb, List<String> listTime){
        this.subjects = subjects;
        this.classNumb = classNumb;
        this.listTime = listTime;



        Log.d("table", subjects.get(0));

        getView().setDate(subjects, classNumb, listTime);

    }

    public void getDate(){

        getView().getDate();


//        getView().setDate(subjects, classNumb);
//        getView().showToast(subjects.get(0));

    }

}
