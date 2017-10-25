package com.schoolInfo.bartosz.schoolinfo.Home.Timetable;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Calendar;

class TimetablePresenter extends MvpBasePresenter<TimetableView> {

    void recognizeDayOfWeek(){

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);



        if(Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour < 15){
            if(getView() != null)
                getView().toggleMonday();


        }else if(Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour > 15 ||
                Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour < 15){
            if(getView() != null)
                getView().toggleTuesday();


        }else if(Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour > 15 ||
                Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour < 15){
            if(getView() != null)
                getView().toggleWednesday();


        }else if(Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour > 15 ||
                Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour < 15){
            if(getView() != null)
                getView().toggleThursday();


        }else if(Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour > 15 ||
                Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour < 15){
            if(getView() != null)
                getView().toggleFriday();


        }else if(Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK) && hour > 15){
            if(getView() != null)
                getView().toggleMonday();


        }else if(Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)){
            if(getView() != null)
                getView().toggleMonday();


        } else if(Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)){
            if(getView() != null)
                getView().toggleMonday();}

    }


}
