package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.EditTimetableItem;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableField;
import com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.TimetableFragment;


public class TimetableEditItemAdapter extends MvpBasePresenter<TimetableEditItemView> {



    public void sendTimetableField(int subject, int day, int sort, String room) {

        TimetableField timetableField = new TimetableField(MainActivity.TOKEN, MainActivity.ACTIVE_USER_CLASS, day, subject, sort, room);

        getView().sendTimetableField();
    }
}
