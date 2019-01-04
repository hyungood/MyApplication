package com.example.user.myapplication;
import android.animation.Animator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
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

public class score extends AppCompatActivity {
    TextView puz;
    TextView tet;
    TextView mat;
    TextView ord;

    String token;
    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    Gson gson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        puz = findViewById(R.id.puz_score);
        tet = findViewById(R.id.tet_score);
        mat = findViewById(R.id.mat_score);
        ord = findViewById(R.id.ord_score);

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

        myScore(token);
    }

    // 내 점수
    private void myScore(String token) {
        Call<myscore> comment = apiService.myScore(token);

        comment.enqueue(new Callback<myscore>() {
            @Override
            public void onResponse(Call<myscore> call, Response<myscore> response) {
                myscore m = response.body();

                if (m.getMessage().equals("MyScore")) {
                    puz.setText(Integer.toString(m.getData().getPuzzle()));
                    tet.setText(Integer.toString(m.getData().getTetris()));
                    mat.setText(Integer.toString(m.getData().getShape()));
                    ord.setText(Integer.toString(m.getData().getOrdering()));

                    Toast.makeText(getApplicationContext(), "내 점수 보기!", Toast.LENGTH_LONG).show();
                } else if (m.getMessage().equals("Token Error")) {
                    Toast.makeText(getApplicationContext(), "토큰에러 :)", Toast.LENGTH_LONG).show();
                } else if (m.getMessage().equals("DB Connection Fail")) {
                    Toast.makeText(getApplicationContext(), "디비에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<myscore> call, Throwable t) {
                Log.d("myscoreRetrofit", t.toString());
            }
        });
    }
}