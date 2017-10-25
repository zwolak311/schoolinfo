package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.EditTimetableItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class TimetableEditItemActivity extends MvpActivity<TimetableEditItemView, TimetableEditItemAdapter> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);
        ButterKnife.bind(this);



    }

    @NonNull
    @Override
    public TimetableEditItemAdapter createPresenter() {
        return new TimetableEditItemAdapter();
    }


}
