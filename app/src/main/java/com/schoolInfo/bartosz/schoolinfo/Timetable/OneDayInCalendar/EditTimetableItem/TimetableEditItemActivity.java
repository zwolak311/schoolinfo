package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.EditTimetableItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class TimetableEditItemActivity extends MvpActivity<TimetableEditItemView, TimetableEditItemAdapter> implements TimetableEditItemView {
    @BindView(R.id.edit_timetable_subject) Spinner subjectEdit;
    @BindView(R.id.edit_timetable_classNumber) EditText classNumber;
    @BindView(R.id.edit_timetable_startHour) EditText startHour;
    @BindView(R.id.edit_timetable_periodTime) EditText periodTime;
    @BindView(R.id.edit_timetable_teacher) EditText teacher;
    int subject, day, sort;
    String room;
    ArrayList<String> subjectList = new ArrayList<>();
    ArrayList<CharSequence> subjects = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);
        ButterKnife.bind(this);



        subjects = getIntent().getCharSequenceArrayListExtra("array");


        try {
            for (CharSequence charSequence : subjects){
                subjectList.add(charSequence.toString());
            }
        }catch (Exception ignore){}


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, subjectList);
        subjectEdit.setAdapter(adapter);

        classNumber.setText(getIntent().getStringExtra("classNum"));
        startHour.setText(getIntent().getStringExtra("startTime"));
        periodTime.setText(getIntent().getStringExtra("period"));
        teacher.setText("");

    }

    @NonNull
    @Override
    public TimetableEditItemAdapter createPresenter() {
        return new TimetableEditItemAdapter();
    }


    @Override
    public void sendTimetableField() {

        Intent intent = new Intent();
        intent.putExtra("id", 2);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timetable_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        subject = getIntent().getIntExtra("subjectId", -1);
        day = getIntent().getIntExtra("dayID", -1);
        sort = getIntent().getIntExtra("sort", -1);
        room = getIntent().getStringExtra("classNum");


        presenter.sendTimetableField(subject, day, sort, room);

        return super.onOptionsItemSelected(item);
    }
}
