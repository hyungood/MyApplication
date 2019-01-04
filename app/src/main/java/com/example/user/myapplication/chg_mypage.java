package com.example.user.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chg_mypage extends AppCompatActivity{
    EditText editID;
    EditText editName;
    EditText editPhone;
    EditText editPwd;
    EditText editBirth;

    String token;
    int pwd;
    String birth;
    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    Gson gson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chg_mypage);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.change_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        editPwd = (EditText) findViewById(R.id.Pw);
        editBirth = (EditText) findViewById(R.id.Bir);

        editID = (EditText) findViewById(R.id.Id);
        editID.setFocusable(false);
        editID.setEnabled(false);
        editID.setCursorVisible(false);
        editID.setKeyListener(null);
        editID.setBackgroundColor(Color.TRANSPARENT);

        editName = (EditText) findViewById(R.id.Name);
        editName.setFocusable(false);
        editName.setEnabled(false);
        editName.setCursorVisible(false);
        editName.setKeyListener(null);
        editName.setBackgroundColor(Color.TRANSPARENT);

        editPhone = (EditText) findViewById(R.id.Phone);
        editPhone.setFocusable(false);
        editPhone.setEnabled(false);
        editPhone.setCursorVisible(false);
        editPhone.setKeyListener(null);
        editPhone.setBackgroundColor(Color.TRANSPARENT);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);

        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPref.edit();
        token = mPref.getString("token", token);
        editor.putString("token", token);
        editor.commit();

        myProfile(token);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.button_chg) {
            pwd = Integer.parseInt(editPwd.getText().toString());
            birth = editBirth.getText().toString();

            edit(token, pwd, birth);
        }
    }

    // 내 정보
    private void myProfile(String token) {
        Call<profile> comment = apiService.myProfile(token);

        comment.enqueue(new Callback<profile>() {
            @Override
            public void onResponse(Call<profile> call, Response<profile> response) {
                profile p = response.body();

                if (p.getMessage().equals("Profile")) {
                    editID.setText(p.getData().getId());
                    editName.setText(p.getData().getName());
                    editPhone.setText(p.getData().getPhone());

                    Toast.makeText(getApplicationContext(), "내 정보 보기!", Toast.LENGTH_LONG).show();
                } else if (p.getMessage().equals("Token Error")) {
                    Toast.makeText(getApplicationContext(), "토큰에러 :)", Toast.LENGTH_LONG).show();
                } else if (p.getMessage().equals("DB Connection Fail")) {
                    Toast.makeText(getApplicationContext(), "디비에러", Toast.LENGTH_LONG).show();
                } else if(p.getMessage().equals("Internal Server Error")){
                    Toast.makeText(getApplicationContext(), "서버에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<profile> call, Throwable t) {
                Log.d("profileRetrofit", t.toString());
            }
        });
    }

    // 내 정보 수정
    private void edit(String token, int pwd, String birth) {
        Call<edit> comment = apiService.edit(token, pwd, birth);

        comment.enqueue(new Callback<edit>() {
            @Override
            public void onResponse(Call<edit> call, Response<edit> response) {
                edit e = response.body();

                if (e.getMessage().equals("Edit Success")) {
                    finish();
                    Toast.makeText(getApplicationContext(), "내 정보 수정 성공!", Toast.LENGTH_LONG).show();
                } else if (e.getMessage().equals("Null Value")) {
                    Toast.makeText(getApplicationContext(), "망 :)", Toast.LENGTH_LONG).show();
                } else if (e.getMessage().equals("Token Error")) {
                    Toast.makeText(getApplicationContext(), "토큰에러", Toast.LENGTH_LONG).show();
                } else if(e.getMessage().equals("Internal Server Error")){
                    Toast.makeText(getApplicationContext(), "서버에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<edit> call, Throwable t) {
                Log.d("profileEditRetrofit", t.toString());
            }
        });
    }
}