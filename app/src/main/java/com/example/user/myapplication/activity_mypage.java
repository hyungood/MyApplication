package com.example.user.myapplication;
import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class activity_mypage extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;

    TextView showN, showI;

    String token;
    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        showN = (TextView) findViewById(R.id.name);
        showI = (TextView) findViewById(R.id.id);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.page_bar);
//        setSupportActionBar(myToolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setElevation(0);

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
        editor.commit();

        btn1 = (Button)findViewById(R.id.gameTime);
        btn2 = (Button)findViewById(R.id.contractChk);
        btn3 = (Button)findViewById(R.id.score);
        btn4 = (Button)findViewById(R.id.change);

        showUser(token);
    }

    public void onClick(View view){
        int i = view.getId();
        switch (i) {
            case R.id.gameTime:
                Intent y = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(y);
                break;
            case R.id.contractChk:
                Intent m = new Intent(getApplicationContext(), listConActivity.class);
                startActivity(m);
                break;
            case R.id.score:
                Intent k = new Intent(getApplicationContext(), score.class);
                startActivity(k);
                break;
            case R.id.change:
                Intent j = new Intent(getApplicationContext(), chg_mypage.class);
                startActivity(j);
                break;

            default:
                break;


        }

    }

    private void showUser(String token){
        Log.d("token",token);
        Call<show> comment = apiService.showUser(token);
        comment.enqueue(new Callback<show>() {
            @Override
            public void onResponse(Call<show> call, Response<show> response) {
                show s = response.body();
                Log.d("하", s.getMessage());

                if(s.getMessage().equals("Show user ok")){
                    showI.setText("ID: "+s.getData().getId());
                    showN.setText("NAME: "+s.getData().getName());

                    Toast.makeText(getApplicationContext(), "내 정보 출력 확인", Toast.LENGTH_LONG).show();
                } else if(s.getMessage().equals("Error getting show user")){
                    Toast.makeText(getApplicationContext(),"서버 에러", Toast.LENGTH_LONG).show();

                } else if(s.getMessage().equals("token err")){
                    Toast.makeText(getApplicationContext(),"토큰 에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<show> call, Throwable t) {

            }
        });
    }
}