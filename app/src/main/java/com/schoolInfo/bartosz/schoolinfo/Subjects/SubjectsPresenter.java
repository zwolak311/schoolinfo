package com.schoolInfo.bartosz.schoolinfo.Subjects;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectAddPOJO;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SubjectsPresenter extends MvpBasePresenter<SubjectsView> {
    SubjectList subjectList;
    RestInterface api;


    public SubjectsPresenter() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);
        okHttpClient.readTimeout(5, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(5, TimeUnit.SECONDS);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://school-info.c0.pl/public/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = builder.create(RestInterface.class);


    }

    public void setSubjectList(SubjectList subjectList) {
        this.subjectList = subjectList;

        setView();
    }

    private void setView() {

        if (getView() != null) {

            if (subjectList != null)
                getView().setViewOfSubjectList(subjectList);
            else if(subjectList == null)
                getView().networkNotAvailable();
            else if (subjectList.getMessage().size() == 0)

                getView().listIsEmpty();



        }
    }
}
