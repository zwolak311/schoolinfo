package com.schoolInfo.bartosz.schoolinfo.AddedInfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.InfoAndToken;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddedDialog extends DialogFragment {
    @BindView(R.id.addedDialogSubject) AutoCompleteTextView subjectText;
    @BindView(R.id.addedDialogContent) TextView contentText;
    @BindView(R.id.addedDialogTitle) TextView title;
    @BindView(R.id.addedDialogIcon) ImageView dateView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_send_layout, null);
        ButterKnife.bind(this, view);


        String[] subjects = getResources().getStringArray(R.array.subject_array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),  android.R.layout.simple_list_item_1, subjects);
        subjectText.setAdapter(arrayAdapter);


        Calendar calendar = Calendar.getInstance();

        try{
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.getPresenter().setCalendar(calendar);
        }catch (Exception ignore){}


        title.setText(getTag());
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getChildFragmentManager(), "datePicker");


            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Wyślij", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        InfoAndToken infoAndToken = new InfoAndToken();
                        infoAndToken.setSubject(subjectText.getText().toString());
                        infoAndToken.setContent(contentText.getText().toString());


                        switch (getTag()){
                            case "Inne":
                                infoAndToken.setType("news");
                                break;
                            case "Sprawdzian":
                                infoAndToken.setType("exam");
                                break;
                            case "Praca domowa":
                                infoAndToken.setType("homework");
                                break;


                        }

                        MainActivity mainActivity = (MainActivity) getActivity();
//                        mainActivity.showSwipeRefreshLayout();
                        mainActivity.getPresenter().sendInfo(infoAndToken);


                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



        return builder.create();
    }
}
