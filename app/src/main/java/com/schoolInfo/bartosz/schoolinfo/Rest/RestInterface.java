package com.schoolInfo.bartosz.schoolinfo.Rest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestInterface {


    @POST("api/auth/signin")
    Call<LogInCallbackBody> signIn(@Body SignInUser signInUser);

    @POST("api/register")
    Call<LogInCallbackBody> register(@Body Register register);


    @POST("api/profile")
    Call<UserBaseInformation> getUserInformation(@Body Token token);

    @POST("api/group/{className}")
    Call<POJOClassInfo> getClassInformation(@Path("className") String className, @Body Token token);


    @POST("api/group/list")
    Call<Status> getGroupList(@Body Token token);

    @POST("api/requests/group")
    Call<Requests> getRequestGroupList(@Body Token token);

    @POST("api/group/join/{className}")
    Call<Status> addToGroup(@Path("className") String className, @Body Token token);

    @POST("api/group/members/{className}/{option}")
    Call<Status> responseAtAddToGroupRequest(@Path("className") String className, @Path("option") String option, @Body TokenAndId token);


    @Headers("Content-Type: application/json")
    @POST("api/group/{className}/add")
    Call<Status> sendInfo(@Path("className") String className, @Body InfoAndToken infoAndToken);

    @Headers("Content-Type: application/json")
    @POST("api/group/{className}/delete")
    Call<Status> deleteInfo(@Path("className") String className, @Body ArrayIdAndToken arrayIdAndToken);

}
