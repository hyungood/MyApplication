package com.example.user.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {


    EditText ipId, ipPassword;
    Button registerBtn;
    Button loginBtn;
    String myId;
    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    MainActivity mainActivity;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mainActivity = new MainActivity();
        login = mainActivity.login;
        ipId = (EditText) findViewById(R.id.id);
        ipPassword = (EditText) findViewById(R.id.password);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        registerBtn = (Button) findViewById(R.id.button_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);


        loginBtn = findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myId = ipId.getText().toString().trim();
                Log.d("내아이디",myId);
                int pwd = Integer.parseInt(ipPassword.getText().toString());

                signin(myId, pwd);

            }
        });


    }


    private void signin(final String myId, final int pwd) {
        // Tag used to cancel the request
        String tag_string_req = "req_signin";
        Log.d("로그인 시도","로그인을 해보자");
        Call<userConnect> comment2 = apiService.signin(myId, pwd);
        comment2.enqueue(new Callback<userConnect>() {
            @Override
            public void onResponse(Call<userConnect> call, Response<userConnect> response) {
//                Log.v("Test", "Response Body is" + response.body().toString());
                userConnect u = response.body();

                if( u.getMessage().equals("Login success")) {
                    String token = u.getToken();
                    mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("token", token);
                    editor.commit();
                    Log.e("토쿤",token);
                    Toast.makeText(getApplicationContext(), "로그인완료!", Toast.LENGTH_LONG).show();

                    if(u.getAlert().getContractNum()==0)
                    {
                        Intent j = new Intent(getApplicationContext(), MainActivity.class);
                        j.putExtra("로그아웃", "로그아웃");
                        startActivity(j);
                        //login.setText("로그아웃");
                    }
                    else{
                        Intent i = new Intent(getApplicationContext(), alert.class);
                        i.putExtra("howmany", u.getAlert().getContractNum());
                        startActivity(i);
                    }

                }

                else if(u.getMessage().equals("Null Value")){
                    Toast.makeText(getApplicationContext(), "망 :)", Toast.LENGTH_LONG).show();
                }
                else if(u.getMessage().equals("Login Error")) {
                    Toast.makeText(getApplicationContext(), "로그인에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<userConnect> call, Throwable t) {
                Log.d("userRetrofit", t.toString());
            }


        });



    }
}