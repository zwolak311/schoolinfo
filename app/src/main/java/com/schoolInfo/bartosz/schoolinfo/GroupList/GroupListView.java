package com.schoolInfo.bartosz.schoolinfo.GroupList;

import com.schoolInfo.bartosz.schoolinfo.Rest.Requests;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;


public interface GroupListView extends MvpView {

    void setGroupList(Status status, Requests requests);

    void networkNotAvailable();

    void getUBI();
}
