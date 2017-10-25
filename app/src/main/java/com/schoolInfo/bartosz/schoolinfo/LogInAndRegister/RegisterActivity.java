package com.schoolInfo.bartosz.schoolinfo.LogInAndRegister;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.LogInCallbackBody;
import com.schoolInfo.bartosz.schoolinfo.Rest.Register;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.usernameRegister) EditText nick;
    @BindView(R.id.nameRegister) EditText name;
    @BindView(R.id.secondNameRegister) EditText secondName;
    @BindView(R.id.emailRegister) EditText email;
    @BindView(R.id.passwordRegister) EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.forwardButton)
    void forward(){

        attemptLogin();

//        startActivity(new Intent(this, RegisterActivityEmail.class));
    }

    @OnClick(R.id.logInButtonRegister)
    void logIn(){

        startActivity(new Intent(this, LoginActivitySecond.class));
        finish();

    }


    private void attemptLogin() {


        // Reset errors.
//        email.setError(null);
//        password.setError(null);

        // Store values at the time of the login attempt.
        String nickString = nick.getText().toString();
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String nameString = name.getText().toString();
        String secondNameString = secondName.getText().toString();

        boolean cancel = false;
        View focusView = null;

//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        if(TextUtils.isEmpty(nickString)){
            nick.setError("To pole jest wymagane!");
            focusView = nick;
            cancel = true;

        }else if(isNickValid(nickString)){
            nick.setError("To pole nie może zawierać spacji!");
            focusView = nick;
            cancel = true;

        }else if(TextUtils.isEmpty(nameString)){
            name.setError("To pole jest wymagane!");
            focusView = name;
            cancel = true;

        }else if(TextUtils.isEmpty(secondNameString)){
            secondName.setError("To pole jest wymagane!");
            focusView = secondName;
            cancel = true;

        }else if (TextUtils.isEmpty(emailString)) {
            email.setError("To pole jest wymagane!");
            focusView = email;
            cancel = true;

        } else if (!isEmailValid(emailString)) {
            email.setError("Niepoprawny adres email");
            focusView = email;
            cancel = true;

        }else if(!isPasswordValid(passwordString)){
            password.setError("Conajmniej 8 znaków!");
            focusView = password;
            cancel = true;

        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);


            logInToServe(nickString, nameString, secondNameString, emailString, passwordString);
        }
    }

    private boolean isNickValid(String nick) {
        //TODO: Replace this with your own logic
        return nick.contains(" ");
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 8;
    }


    private void logInToServe(String nick, String name, String secondName, final String email, final String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Poczekaj, rejestracja w toku...");
        progressDialog.show();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);

        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://school-info.c0.pl/public/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        RestInterface api = builder.create(RestInterface.class);


        final Call<LogInCallbackBody> register = api.register(new Register(nick, name, secondName, email, password));


        register.enqueue(
                new Callback<LogInCallbackBody>() {
                    @Override
                    public void onResponse(Call<LogInCallbackBody> call, Response<LogInCallbackBody> response) {

                        if(response.body().isStatus()) {

                            SharedPreferences preferences = getSharedPreferences(getString(R.string.token_string), MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString(getString(R.string.token_string), response.body().getToken());
                            editor.putBoolean("status", response.body().isStatus());

                            editor.apply();
                            editor.commit();

                            progressDialog.dismiss();

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            // set the new task and clear flags
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();


                        }else if(response.body().getMessage().getUsername()!= null){
                            progressDialog.dismiss();
                            messageDialog(response.body().getMessage().getUsername());

                        } else if(response.body().getMessage().getName()!= null){
                            progressDialog.dismiss();
                            messageDialog(response.body().getMessage().getName());

                        } else if(response.body().getMessage().getSecondName()!= null){
                            progressDialog.dismiss();
                            messageDialog(response.body().getMessage().getSecondName());

                        }else if(response.body().getMessage().getEmail()!= null){
                            progressDialog.dismiss();
                            messageDialog(response.body().getMessage().getEmail());

                        }else if(response.body().getMessage().getPassword()!= null){
                            progressDialog.dismiss();
                            messageDialog(response.body().getMessage().getPassword());
                        }

                    }

                    @Override
                    public void onFailure(Call<LogInCallbackBody> call, Throwable t) {

                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("Logowanie nie powiodło się.");
                        builder.setMessage("Sprawdź połączenie z internetem.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.show();
                        dialog.show();

                        Toast.makeText(RegisterActivity.this, " sds " + t.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );


    }

    public void messageDialog(String s){

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Rejestracja nie powiodła się.");
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.show();
        dialog.show();

    }

}

