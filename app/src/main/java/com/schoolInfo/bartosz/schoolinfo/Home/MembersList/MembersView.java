package com.schoolInfo.bartosz.schoolinfo.Home.MembersList;

import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;


public interface MembersView extends MvpView {

    void getMembersList();

    void setMembersList(ArrayList<POJOClassInfo.Members> membersArrayList, ArrayList<POJOClassInfo.Requests> requestsArrayList);

    void networkIsNotAvailable();

    void refresh();

}
