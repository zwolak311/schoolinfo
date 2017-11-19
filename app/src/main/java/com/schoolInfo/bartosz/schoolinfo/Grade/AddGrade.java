package com.schoolInfo.bartosz.schoolinfo.Grade;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;


public class AddGrade extends DialogFragment {
    @BindView(R.id.spinner) Spinner spinner;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.grade_add_new, null);
        ButterKnife.bind(this, view);

        MainActivity mainActivity = (MainActivity) getActivity();
        ArrayList<String> strings = new ArrayList<>();


        for (SubjectList.Message message : mainActivity.getPresenter().loadSubjectList().getMessage()) {
            strings.add(message.getName());
        }

//        mainActivity.getPresenter().loadSubjectList();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Dodaj ocene")
                .setView(view)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    @OnItemSelected(R.id.spinner)
    void spiner(){



    }
}
