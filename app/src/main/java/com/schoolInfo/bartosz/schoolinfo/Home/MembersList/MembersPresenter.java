package com.schoolInfo.bartosz.schoolinfo.Home.MembersList;

import android.support.v4.widget.SwipeRefreshLayout;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.schoolInfo.bartosz.schoolinfo.Rest.TokenAndId;
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


public class MembersPresenter extends MvpBasePresenter<MembersView> {
    RestInterface api;
    TokenAndId tokenAndId;
    ArrayList<POJOClassInfo.Members> membersArrayList;
    ArrayList<POJOClassInfo.Requests> requestsArrayList;



    public MembersPresenter() {

        tokenAndId = new TokenAndId();
        tokenAndId.setToken(MainActivity.TOKEN);

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

    }

    public void setMembersList(MainInformationAboutUserAndClass mainInformationAboutUserAndClass) {


        if (getView() != null) {

            if (!mainInformationAboutUserAndClass.isDateEmpty()) {
                POJOClassInfo pojoClassInfo = mainInformationAboutUserAndClass.getPojoClassInfo();

                membersArrayList = pojoClassInfo.getMembers();
                requestsArrayList = pojoClassInfo.getRequests();


                if (pojoClassInfo.getMembers().size() != 0)
                    getView().setMembersList(membersArrayList, requestsArrayList);
                else
                    getView().networkIsNotAvailable();


            } else
                getView().networkIsNotAvailable();

        }
    }

    public void acceptUser(int id) {

        tokenAndId.setId(id);

        Call<Status> acceptUser = api.responseAtAddToGroupRequest(MainActivity.ACTIVE_USER_CLASS, "accept", tokenAndId);

        acceptUser.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                getView().refresh();

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                getView().refresh();


            }
        });

    }

    public void refuseUser(int id) {


        tokenAndId.setId(id);

        Call<Status> refuseUser = api.responseAtAddToGroupRequest(MainActivity.ACTIVE_USER_CLASS, "refuse", tokenAndId);


        refuseUser.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                getView().refresh();


            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                getView().refresh();


            }
        });

    }

    public void setRefreshing(boolean b) {

    }
}
