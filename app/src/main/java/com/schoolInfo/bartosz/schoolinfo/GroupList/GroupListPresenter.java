package com.schoolInfo.bartosz.schoolinfo.GroupList;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Rest.Requests;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.schoolInfo.bartosz.schoolinfo.Rest.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GroupListPresenter extends MvpBasePresenter<GroupListView> {
    private RestInterface api;
    Token token;
    Status status;
    Requests requests;

    public GroupListPresenter() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);
        okHttpClient.readTimeout(15, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(15, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://school-info.c0.pl/public/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = builder.create(RestInterface.class);

        token = new Token(MainActivity.TOKEN);


    }

    public void getGroupList() {


        final Call<Status> getList = api.getGroupList(token);


        getList.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                status = response.body();

                getRequestGroupList();

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                getView().networkNotAvailable();

            }
        });




    }

    public void getRequestGroupList(){

        Call<Requests> getRequestGroupList = api.getRequestGroupList(token);

        getRequestGroupList.enqueue(new Callback<Requests>() {
            @Override
            public void onResponse(Call<Requests> call, Response<Requests> response) {
                requests = response.body();

                if(getView() != null ) {
                    if (status != null && requests != null)
                        getView().setGroupList(status, requests);
                    else
                        getView().networkNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Requests> call, Throwable t) {
                getView().networkNotAvailable();

            }
        });

    }






    public void addToGroup(int position) {


        Call<Status> addToGroup = api.addToGroup(status.getMessage().get(position).getGroupname(), token);


        addToGroup.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

//                getGroupList();
                getView().getUBI();

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                getGroupList();

            }
        });



    }
}
