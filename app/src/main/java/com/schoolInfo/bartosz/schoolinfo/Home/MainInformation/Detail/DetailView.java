package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.Detail;

import com.hannesdorfmann.mosby.mvp.MvpView;



 interface DetailView extends MvpView {

  void setDetailInfo(String subject, String date, String content, String typeOfInfo);

  void setNewDate(String s);

  void changesAreSave();

  void showToast(String string);
 }
