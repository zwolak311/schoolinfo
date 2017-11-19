package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;


public interface TimetableView extends MvpView {

    void showToast(String s);

    void setDate(List<String> subjects, List<String> classNumb, List<String> listTime);

    void getDate();

    void setTimetableEditDate(int subject, int dayId , int sort, String classNum);

}
