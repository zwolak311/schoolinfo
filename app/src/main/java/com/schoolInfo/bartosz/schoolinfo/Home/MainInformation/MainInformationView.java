package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;


public interface MainInformationView extends MvpView {


//    void getRefreshedPOJOClassInfo();


    void setRecycleList(POJOClassInfo pojoClassInfo, ArrayList<POJOClassInfo.Information> informationArrayList);

    void listIsEmpty(String name);

    void networkNotAvailable();

    void acceptInfo();

    void doNotHavePermissionInfo();

    void showCircle(int position, boolean[] boolForHomework);

    void hideCircle();

    void showDetailInfo(int id,String typeOfInfo, String subject, String date, String content);

    void setRefreshing(boolean refreshing);

}
