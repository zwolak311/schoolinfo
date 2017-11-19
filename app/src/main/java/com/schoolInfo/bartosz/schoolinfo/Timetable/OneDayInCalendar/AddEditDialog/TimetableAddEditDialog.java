package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.AddEditDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableField;
import com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.TimetableFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimetableAddEditDialog extends DialogFragment {
    @BindView(R.id.timetableAddSpinner) Spinner spinner;
    int subjectId;


    public static TimetableAddEditDialog newInstance(int subjectId, int dayId, int sort, String room) {
        TimetableAddEditDialog f = new TimetableAddEditDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("subjectId", subjectId);
        args.putInt("dayId", dayId);
        args.putInt("sort", sort);
        args.putString("room", room);
        f.setArguments(args);

        return f;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.timetable_add_edit_dialog, null);
        ButterKnife.bind(this, view);


        final MainActivity mainActivity = (MainActivity) getActivity();
        ArrayList<String> strings = new ArrayList<>();
        final ArrayList<SubjectList.Message> message = mainActivity.getPresenter().loadSubjectList().getMessage();
        subjectId = getArguments().getInt("subjectId");

        int i = -1;
        int position = 0;
        for (SubjectList.Message messageLoop : message) {
            i++;
            if(subjectId == messageLoop.getId())
                position = i;

            strings.add(messageLoop.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);

        spinner.setSelection(position);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                subjectId = message.get(adapterView.getSelectedItemPosition()).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Edytuj plan lekcji")
                .setView(view)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TimetableFragment timetableFragment = (TimetableFragment) getParentFragment();
                        timetableFragment.setRefreshing(true);


                        TimetableField timetableField = new TimetableField(
                                MainActivity.TOKEN,
                                MainActivity.ACTIVE_USER_CLASS,
                                subjectId,
                                getArguments().getInt("dayId"),
                                getArguments().getInt("sort"),
                                getArguments().getString("room"));


                        mainActivity.getPresenter().sendTimetableField(timetableField);



                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


        if(subjectId!=-1){


                builder.setNeutralButton("Usuń", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    TimetableFragment timetableFragment = (TimetableFragment) getParentFragment();
                    timetableFragment.setRefreshing(true);

                    TimetableField timetableField = new TimetableField(
                            MainActivity.TOKEN,
                            MainActivity.ACTIVE_USER_CLASS,
                            subjectId,
                            getArguments().getInt("dayId"),
                            getArguments().getInt("sort"),
                            getArguments().getString("room"));


                    mainActivity.getPresenter().removeTimetableField(timetableField);

                }
            });


        }

        return builder.create();
    }
}
