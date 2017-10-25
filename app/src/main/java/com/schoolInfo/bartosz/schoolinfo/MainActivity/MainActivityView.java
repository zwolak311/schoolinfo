package com.schoolInfo.bartosz.schoolinfo.MainActivity;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;
import com.hannesdorfmann.mosby.mvp.MvpView;


public interface MainActivityView extends MvpLceView<MainInformationAboutUserAndClass> {


    void setClassInformation(POJOClassInfo postClass);
    void setBaseInformation(UserBaseInformation userInformation);
    void networkNotAvailable(String s);
    void getInfoForDelete();
//    void refreshUBI();


}
