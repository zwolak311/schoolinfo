package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation;

import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;


public class MainInformationPresenter extends MvpBasePresenter<MainInformationView> {
    private POJOClassInfo pojoClassInfo;
    private UserBaseInformation UBI;


    private ArrayList<POJOClassInfo.Information> informationArrayList;
    private boolean[] boolForHomework;




    public void setRecycleView(MainInformationAboutUserAndClass mainInformationAboutUserAndClass){

        if (getView() != null) {


            if(mainInformationAboutUserAndClass != null) {
                this.pojoClassInfo = mainInformationAboutUserAndClass.getPojoClassInfo();
                this.UBI = mainInformationAboutUserAndClass.getUBI();

                informationArrayList = new ArrayList<>();

                for (int i = 0; i < pojoClassInfo.getInformations().size(); i++)
                    if (pojoClassInfo.getInformations().get(i).getVisibility() == 1)
                        informationArrayList.add(pojoClassInfo.getInformations().get(i));


                if (informationArrayList.size() == 0)
                    getView().listIsEmpty(pojoClassInfo.getGroupname());
                else
                    getView().setRecycleList(pojoClassInfo, informationArrayList);


                boolForHomework = new boolean[informationArrayList.size()];
//                boolForHomework = new boolean[3];

            }else
                getView().networkNotAvailable();
        }
    }


    void acceptInfo(){

        if(getView() != null) {

            if (pojoClassInfo.getOwner().getId().equals(UBI.getUser().getId()))
                getView().acceptInfo();
            else
                getView().doNotHavePermissionInfo();

        }
    }





    public ArrayList<Integer> getHomeworkIdArrayListForDelete() {
        ArrayList<Integer> homeworkIdArrayListForDelete = new ArrayList<>();

        getView().setRefreshing(true);

        for (int i = 0; i < boolForHomework.length; i++)
            if(boolForHomework[i])
                homeworkIdArrayListForDelete.add(informationArrayList.get(i).getId());


        return homeworkIdArrayListForDelete;
    }



    void showCircle(int position){

        boolForHomework = new boolean[informationArrayList.size()];

        for (int i = 0; i < boolForHomework.length ; i++) {
            boolForHomework[i] = false;
        }

//        for (boolean bool:boolForHomework)
//            bool = false;

        boolForHomework[position] = true;

        if(getView() != null)
            getView().showCircle(position, boolForHomework);

    }



    public void hideCircle() {
        if(getView() != null)
            getView().hideCircle();
    }



    void circleIsCheck(int position){
        boolForHomework[position] = true;
    }

    void circleIsUnCheck(int position) {
        boolForHomework[position] = false;

        boolean isSomethingChecked = false;

        for (boolean aBoolForHomework : boolForHomework)
            if (aBoolForHomework)
                isSomethingChecked = true;


        if(!isSomethingChecked) {
            if(getView() != null)
                getView().hideCircle();
        }
    }

    void informationDetail(int position){

        if(getView() != null)
            getView().showDetailInfo(informationArrayList.get(position).getId(),
                    informationArrayList.get(position).getType(),
                    informationArrayList.get(position).getSubject(),
                    informationArrayList.get(position).getDate(),
                    informationArrayList.get(position).getContent());

    }


}

