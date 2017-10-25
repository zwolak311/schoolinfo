package com.schoolInfo.bartosz.schoolinfo.Home.Timetable;

import com.hannesdorfmann.mosby.mvp.MvpView;

interface TimetableView extends MvpView {

    void toggleMonday();
    void toggleTuesday();
    void toggleWednesday();
    void toggleThursday();
    void toggleFriday();

}
