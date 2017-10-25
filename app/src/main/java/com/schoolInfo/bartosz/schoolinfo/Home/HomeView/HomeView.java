package com.schoolInfo.bartosz.schoolinfo.Home.HomeView;

import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;


public interface HomeView extends MvpView {
    void showToast(String s);


    void setRecycleView(String dayOfWeek, List<String> subjectList, List<String> classList, ArrayList<POJOClassInfo.Information> information);

    void networkNotAvailable();
}
