package com.schoolInfo.bartosz.schoolinfo.Subjects;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectAddPOJO;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class AddSubject extends DialogFragment {


    public static AddSubject newInstance(String subject, int id) {
        AddSubject f = new AddSubject();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("subject", subject);
        args.putInt("id", id);
        f.setArguments(args);

        return f;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.subject_add, null);
        ButterKnife.bind(this, view);

        final String subject = getArguments().getString("subject");
        final int id = getArguments().getInt("id");

        final EditText subjectEdit = (EditText) view.findViewById(R.id.subjectEditAdd);
        subjectEdit.setText(subject);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, strings);
//        spinner.setAdapter(adapter);



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Dodaj przedmiot")
                .setView(view)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setRefreshing(true);
                        mainActivity.getPresenter().sendSubject(subjectEdit.getText().toString());

                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        if(id!=-1){
            builder.setNeutralButton("Usuń przedmiot", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setRefreshing(true);
                    mainActivity.getPresenter().deleteSubject(id);

                }
            });
        }




        return builder.create();

    }
}
