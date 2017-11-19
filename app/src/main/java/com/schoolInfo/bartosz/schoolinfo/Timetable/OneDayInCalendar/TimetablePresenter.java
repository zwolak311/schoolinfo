package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar;

import android.util.Log;
import android.widget.Space;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;

import java.util.ArrayList;
import java.util.List;


public class TimetablePresenter extends MvpBasePresenter<TimetableView> {
    List<String> subjects, classNumb, listTime;
    TimetableMainInformation.Message.Days day;


    public TimetablePresenter() {
    }

    public void setDate(TimetableMainInformation.Message.Days day){
        this.day = day;
        subjects = new ArrayList<>();
        classNumb = new ArrayList<>();
        listTime = new ArrayList<>();

        for (TimetableMainInformation.Message.Days.Subject subject: day.getSubjects()) {
            this.subjects.add(subject.getName());
            this.classNumb.add(subject.getPivot().getRoom());
            this.listTime.add("");
        }


        if(getView()!= null)
            getView().setDate(subjects, classNumb, listTime);

    }

    public void getDate(){

        getView().getDate();


//        getView().setDate(subjects, classNumb);
//        getView().showToast(subjects.get(0));

    }

    public void editTimetable(int adapterPosition) {


        if(adapterPosition < day.getSubjects().size()) {
            TimetableMainInformation.Message.Days.Subject.Pivot pivot = day.getSubjects().get(adapterPosition).getPivot();
            getView().setTimetableEditDate(pivot.getSubject_id(), pivot.getDay_id(), pivot.getSort(), pivot.getRoom());

        }else {
            getView().setTimetableEditDate(-1, day.getId(), adapterPosition + 1, "");
        }



    }
}
