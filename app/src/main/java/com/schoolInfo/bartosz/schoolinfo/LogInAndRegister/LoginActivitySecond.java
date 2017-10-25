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
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.LogInCallbackBody;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.SignInUser;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivitySecond extends AppCompatActivity {
//    EditText emailText;
//    EditText passwordText;
    @BindView(R.id.email) EditText emailText;
    @BindView(R.id.password) EditText passwordText;
//    List<String> emails =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);

    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }



    @OnClick(R.id.logInButton)
    void logIn(){
        attemptLogin();

    }

    public void onClickLoginButton(View v){
        attemptLogin();

    }

    private void attemptLogin() {


        // Reset errors.
//        emailText.setError(null);
//        passwordText.setError(null);

        // Store values at the time of the login attempt.
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailText.setError(getString(R.string.error_field_required));
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError(getString(R.string.error_invalid_email));
            focusView = emailText;
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

            logInToServe(email, password);
        }
    }

    private void logInToServe(String email, final String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Poczekaj, trwa logowanie...");
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


        Call<LogInCallbackBody> signIn = api.signIn(new SignInUser(email, password));


        signIn.enqueue(
                new Callback<LogInCallbackBody>() {
                    @Override
                    public void onResponse(Call<LogInCallbackBody> call, Response<LogInCallbackBody> response) {

                        if(response.body().isStatus()){

                            SharedPreferences preferences = getSharedPreferences(getString(R.string.token_string), MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString(getString(R.string.token_string), response.body().getToken());
                            editor.putBoolean("status", response.body().isStatus());

                            editor.apply();
                            editor.commit();

                            progressDialog.dismiss();

                            startActivity(new Intent(LoginActivitySecond.this, MainActivity.class));
                            Intent i = new Intent(LoginActivitySecond.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();

                        }else {
                            progressDialog.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivitySecond.this);
                            builder.setTitle("Logowanie nie powiodło się.");
                            builder.setMessage("Nieprawidłowy email lub hasło.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            AlertDialog dialog = builder.show();
                            dialog.show();

                        }


//                        if(response.body().isStatus()){
//
//
//                            saveUserDate(response.body());
//
//
//                            startActivity(new Intent(LoginRegisterActivity.this, MainActivity.class));
//                            finish();
//                        }

                    }

                    @Override
                    public void onFailure(Call<LogInCallbackBody> call, Throwable t) {

                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivitySecond.this);
                        builder.setTitle("Logowanie nie powiodło się.");
                        builder.setMessage("Sprawdź połączenie z internetem.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.show();
                        dialog.show();
                    }
                }
        );


    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }



    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivitySecond.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

//        emailText.setAdapter(adapter);
    }


//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }

}

