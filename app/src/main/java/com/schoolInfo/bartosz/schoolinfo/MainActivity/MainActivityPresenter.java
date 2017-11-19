package com.schoolInfo.bartosz.schoolinfo.MainActivity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.schoolInfo.bartosz.schoolinfo.Rest.ArrayIdAndToken;
import com.schoolInfo.bartosz.schoolinfo.Rest.GroupAdd;
import com.schoolInfo.bartosz.schoolinfo.Rest.InfoAndToken;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectAddPOJO;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectRemovePOJO;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableField;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;
import com.schoolInfo.bartosz.schoolinfo.Rest.Token;
import com.schoolInfo.bartosz.schoolinfo.Rest.TokenAndGroupName;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivityPresenter extends MvpBasePresenter<MainActivityView> {
    private UserBaseInformation UBI;
    private POJOClassInfo pojoClassInfo;
    private SubjectList subjectList;
    private RestInterface api;
    private Calendar calendar;
    private String token;
    private TimetableMainInformation timetableMainInformation;
    MainInformationAboutUserAndClass mainInformationAboutUserAndClass;
    private int topScreen;


    MainActivityPresenter() {
        super();

        mainInformationAboutUserAndClass = new MainInformationAboutUserAndClass();
        mainInformationAboutUserAndClass.setDateEmpty(true);

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




    void getInfoAboutUserOnStart(String token, final String className){
        this.token = token;

        final Call<UserBaseInformation> getUserInformation = api.getUserInformation(new Token(token));


        getUserInformation.enqueue(new Callback<UserBaseInformation>() {
            @Override
            public void onResponse(@NonNull Call<UserBaseInformation> call, @NonNull Response<UserBaseInformation> response) {
                UBI = response.body();

                if(className == null) {
                    getInfoAboutClass(response.body().getGroups().get(0).getGroupname());

                }else {
                    getInfoAboutClass(className);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserBaseInformation> call, @NonNull Throwable t) {
                mainInformationAboutUserAndClass = new MainInformationAboutUserAndClass();

//                mainInformationAboutUserAndClass.setDateEmpty(true);

                if(getView() != null) {
                    getView().setData(mainInformationAboutUserAndClass);
                    getView().showError(t, true);
                }

            }
        });


    }


    private void getInfoAboutClass(final String className){

        final Call<POJOClassInfo> getClassInformation = api.getClassInformation(className, new Token(token));

        getClassInformation.enqueue(new Callback<POJOClassInfo>() {
            @Override
            public void onResponse(@NonNull Call<POJOClassInfo> call, @NonNull Response<POJOClassInfo> response) {
                pojoClassInfo = response.body();
                getTimetableDate(className);

                mainInformationAboutUserAndClass.setUBI(UBI);
                mainInformationAboutUserAndClass.setPojoClassInfo(pojoClassInfo);
                mainInformationAboutUserAndClass.setDateEmpty(false);

            }

            @Override
            public void onFailure(@NonNull Call<POJOClassInfo> call, @NonNull Throwable t) {
                mainInformationAboutUserAndClass = new MainInformationAboutUserAndClass();
//                mainInformationAboutUserAndClass.setDateEmpty(true);

                if(getView() != null) {
                    getView().setData(mainInformationAboutUserAndClass);
                    getView().showError(t, true);

                }

            }
        });
    }


    private void loadSubjectList(String groupName){

        TokenAndGroupName tokenAndGroupName = new TokenAndGroupName();
        tokenAndGroupName.setToken(token);
        tokenAndGroupName.setGroupname(groupName);

        Call<SubjectList> getSubjectList = api.getSubjectList(tokenAndGroupName);

        getSubjectList.enqueue(new Callback<SubjectList>() {
            @Override
            public void onResponse(Call<SubjectList> call, Response<SubjectList> response) {
                subjectList = response.body();
                if(getView() != null) {
                    getView().setData(mainInformationAboutUserAndClass);
                    getView().showContent();
                }
            }

            @Override
            public void onFailure(Call<SubjectList> call, Throwable t) {

            }
        });


    }


    public void getTimetableDate(final String groupname){

        TokenAndGroupName tokenAndGroupName = new TokenAndGroupName(token, groupname);

        Log.d("myTimetable", "sss");

        Call<TimetableMainInformation> getDay = api.getDay(tokenAndGroupName);

        getDay.enqueue(new Callback<TimetableMainInformation>() {
            @Override
            public void onResponse(Call<TimetableMainInformation> call, Response<TimetableMainInformation> response) {
                timetableMainInformation = response.body();

                Log.d("myTimetable", "" + response.body().getMessage().get(1).getDays().get(0).getSubjects().get(0).getName());
                loadSubjectList(groupname);


            }

            @Override
            public void onFailure(Call<TimetableMainInformation> call, Throwable t) {
                Log.d("myTimetable", "" + t.toString());
            }
        });

    }




    public void sendInfo(InfoAndToken infoAndToken){

        String date = getCalendar().get(Calendar.YEAR) + "-" +
                (getCalendar().get(Calendar.MONTH) + 1) + "-" +
                getCalendar().get(Calendar.DAY_OF_MONTH);

        infoAndToken.setDate(date);
        infoAndToken.setToken(token);

        Call<Status> sendHomework = api.sendInfo(pojoClassInfo.getGroupname() , infoAndToken);

        sendHomework.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {

//                if(response.body().getStatus().equals("ok"))
                    getInfoAboutClass(pojoClassInfo.getGroupname());

            }

            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {

                getInfoAboutClass(pojoClassInfo.getGroupname());


            }
        });
    }


    void deleteInfo(ArrayList<Integer> informationIdArrayList){

        ArrayIdAndToken arrayIdAndToken = new ArrayIdAndToken();
        arrayIdAndToken.setToken(token);


        if(informationIdArrayList.size() != 0)
            for (int x = 0; x < informationIdArrayList.size(); x++)
                arrayIdAndToken.getId().add(informationIdArrayList.get(x));



        Call<Status> deleteInfo = api.deleteInfo(pojoClassInfo.getGroupname(), arrayIdAndToken);

        deleteInfo.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                getInfoAboutClass(pojoClassInfo.getGroupname());


            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                getInfoAboutClass(pojoClassInfo.getGroupname());


            }
        });


    }




    public void sendSubject(String subject) {

        SubjectAddPOJO subjectAddPOJO = new SubjectAddPOJO(MainActivity.ACTIVE_USER_CLASS, subject, MainActivity.TOKEN);


        Call<Status> sendSubject = api.sendSubject(subjectAddPOJO);


        sendSubject.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                loadSubjectList(MainActivity.ACTIVE_USER_CLASS);

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                loadSubjectList(MainActivity.ACTIVE_USER_CLASS);

            }
        });



    }


    public void deleteSubject(int id) {

        SubjectRemovePOJO subjectRemovePOJO = new SubjectRemovePOJO(MainActivity.ACTIVE_USER_CLASS, MainActivity.TOKEN, id);


        Call<Status> removeSubject = api.removeSubject(subjectRemovePOJO);

        removeSubject.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

//                loadSubjectList(MainActivity.ACTIVE_USER_CLASS);

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {


                loadSubjectList(MainActivity.ACTIVE_USER_CLASS);

            }
        });


//        loadSubjectList(MainActivity.ACTIVE_USER_CLASS);

    }


    public void sendTimetableField(TimetableField timetableField) {


        Call<Status> addTimetableField = api.sendTimetableField(timetableField);


        addTimetableField.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                getView().loadData(false);

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                getView().loadData(false);

            }
        });

    }

    public void removeTimetableField(TimetableField timetableField){

        Call<Status> removeTimetable = api.removeTimetableField(timetableField);

        removeTimetable.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                getView().loadData(false);



            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                getView().loadData(false);


            }
        });

    }

    void deleteButtonIsClick(){
        if(getView() != null)
            getView().getInfoForDelete();
    }


    public void sendNewGroup(String name, String classname) {

        GroupAdd groupAdd = new GroupAdd(MainActivity.TOKEN, name, classname);

        Call<Status> sendNewGroup = api.sendNewGroup(groupAdd);

        sendNewGroup.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                getView().loadData(false);

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                getView().loadData(false);

            }
        });


    }

    public void setTopScreen(int topScreenNumb){
        this.topScreen = topScreenNumb;

        mainInformationAboutUserAndClass.setWithScreenOnTop(topScreenNumb);

        getView().setData(mainInformationAboutUserAndClass);
    }

    public int getTopScreen() {
        return topScreen;
    }

    public MainInformationAboutUserAndClass getMainInformationAboutUserAndClass() {
        return mainInformationAboutUserAndClass;
    }


    public TimetableMainInformation getTimetableMainInformation() {
        return timetableMainInformation;
    }

    public SubjectList loadSubjectList() {
        return subjectList;
    }

    public POJOClassInfo getPojoClassInfo() {
        return pojoClassInfo;
    }

    public UserBaseInformation getUBI() {
        return UBI;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

}
