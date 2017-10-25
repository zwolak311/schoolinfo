package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.Detail;

import android.support.annotation.NonNull;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.Rest.InfoAndToken;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class DetailPresenter extends MvpBasePresenter<DetailView> {
    private String subject, date, content, typeOfInfo;
    private int id;
    private Calendar calendar;
    private RestInterface api;

    DetailPresenter() {
        super();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .create();


        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://school-info.c0.pl/public/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = builder.create(RestInterface.class);
    }



    void getDetailInfo(int id, String typeOfInfo, String subject, String date, String content){
        this.id = id;
        this.typeOfInfo = typeOfInfo;

        String typeOfInfoString;
        if(typeOfInfo.equals(TabFragment.HOMEWORK))
            typeOfInfoString = "Prace domowe";
        else if(typeOfInfo.equals(TabFragment.EXAM))
            typeOfInfoString = "Sprawdziany";
        else
            typeOfInfoString = "News";

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


        if(getView() != null)
            getView().setDetailInfo(subject, date, content, typeOfInfoString);

    }


    void onSaveButtonClick(String typeOfInfo,String subject, String date, String content){

        if(subject.equals(this.subject) &&
                date.equals(this.date) &&
                content.equals(this.content))

//            if(getView() != null)
                getView().showToast("Brak zmian");

        else{




            InfoAndToken infoAndToken = new InfoAndToken(MainActivity.TOKEN, typeOfInfo ,date, subject, content);
            infoAndToken.setId("" + id);

            Call<Status> sendHomework = api.sendInfo(MainActivity.ACTIVE_USER_CLASS, infoAndToken);

            sendHomework.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {

                    if(getView() != null)
                        getView().changesAreSave();

                }

                @Override
                public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {

                    if(getView() != null)
                        getView().changesAreSave();

                }
            });


        }
    }



    Calendar getCalendar() {
        return calendar;
    }

    void setCalendar(Calendar calendar) {
        this.calendar = calendar;


        String s = calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);

        if(getView() != null)
            getView().setNewDate(s);

    }
}
