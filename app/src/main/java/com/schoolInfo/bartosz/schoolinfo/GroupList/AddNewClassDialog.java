package com.schoolInfo.bartosz.schoolinfo.GroupList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddNewClassDialog extends DialogFragment {
    @BindView(R.id.group_name_to_create) EditText groupName;
    @BindView(R.id.group_tag_to_create) EditText groupTag;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.add_new_class, null);
        ButterKnife.bind(this, view);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getTag())
                .setPositiveButton("Stwórz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setRefreshing(true);
                        mainActivity.getPresenter().sendNewGroup(groupName.getText().toString(), groupTag.getText().toString());


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
