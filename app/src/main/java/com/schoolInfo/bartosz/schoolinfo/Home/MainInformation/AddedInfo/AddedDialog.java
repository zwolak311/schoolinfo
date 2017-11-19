package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.AddedInfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.InfoAndToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddedDialog extends DialogFragment {
    @BindView(R.id.addedDialogSubject) AutoCompleteTextView subjectText;
    @BindView(R.id.addedDialogContent) TextView contentText;
    @BindView(R.id.addedDialogTitle) TextView title;
    @BindView(R.id.addedDialogTitleSpinner) Spinner titleSpiner;
    @BindView(R.id.addedDialogIcon) ImageView dateView;
    boolean isEdit = false;
    InfoAndToken infoAndToken = new InfoAndToken();


    public static AddedDialog newInstance(String subject, String content, String date, String typeOfInfo, String id) {
        AddedDialog f = new AddedDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("subject", subject);
        args.putString("content", content);
        args.putString("date", date);
        args.putString("typeOfInfo", typeOfInfo);
        args.putString("id", id);
        f.setArguments(args);

        return f;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_send_layout, null);
        ButterKnife.bind(this, view);


        String[] subjects = getResources().getStringArray(R.array.subject_array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),  android.R.layout.simple_list_item_1, subjects);
        subjectText.setAdapter(arrayAdapter);


        final Calendar calendar = Calendar.getInstance();

        try{
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.getPresenter().setCalendar(calendar);
        }catch (Exception ignore){}



        try{
            subjectText.setText(getArguments().getString("subject"));
            contentText.setText(getArguments().getString("content"));
            infoAndToken.setId(getArguments().getString("id"));

            isEdit = true;
            title.setVisibility(View.GONE);
            titleSpiner.setVisibility(View.VISIBLE);
            ArrayList<String> typeOfInfoStrings = new ArrayList<>();

            typeOfInfoStrings.add("Praca domowa");
            typeOfInfoStrings.add("Sprawdzian");
            typeOfInfoStrings.add("Inne");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, typeOfInfoStrings);
            titleSpiner.setAdapter(adapter);

            switch (getArguments().getString("typeOfInfo")){

                case "exam":
                    titleSpiner.setSelection(1);
                    break;

                case "homework":
                    titleSpiner.setSelection(0);
                    break;

                case "news":
                    titleSpiner.setSelection(2);
                    break;


            }

            titleSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (i){

                        case 0:
                            infoAndToken.setType("homework");
                            break;

                        case 1:
                            infoAndToken.setType("exam");
                            break;

                        case 2:
                            infoAndToken.setType("news");
                            break;


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate;
            try {
                startDate = df.parse(getArguments().getString("date"));
                calendar.setTime(startDate);

            } catch (Exception ignore) {}

            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.getPresenter().setCalendar(calendar);


        }catch (Exception ignore){


        }

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

                        infoAndToken.setSubject(subjectText.getText().toString());
                        infoAndToken.setContent(contentText.getText().toString());


                        if(!isEdit) {
                            switch (getTag()) {
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
                        }

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setRefreshing(true);
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
