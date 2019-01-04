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
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class RegisterF extends AppCompatActivity {
    EditText nameF, phoneF;
    Button register;
    Retrofit retrofit;
    APIservice apiService;
    String token, familyName, familyPhone;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_register);

        nameF =(EditText)findViewById(R.id.inputNameF);
        phoneF=(EditText)findViewById(R.id.inputPhoneF);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.register_bar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS).addNetworkInterceptor(new StethoInterceptor())
                .build();

        Gson gson = new GsonBuilder().setLenient().create();   //json 형식을 매게변수로 만들어.
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);

        Intent intent = getIntent();
        token = intent.getExtras().getString("token");

        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("token", token);
        editor.commit();


        register=(Button)findViewById(R.id.button_rg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                familyName = nameF.getText().toString().trim();
                familyPhone = phoneF.getText().toString().trim();
                Log.d("이름", familyName);
                Log.d("번호", familyPhone);
                if(familyName.equals("")||familyPhone.equals("")){
                    Intent j = new Intent(getApplicationContext(), MainActivity.class);
                    j.putExtra("로그아웃", "로그아웃");
                    startActivity(j);
                }
                else
                    relateFam(token, familyName, familyPhone);
            }
        });
    }

    private void relateFam(final String token, final String familyName, final String familyPhone){
        Call<userConnect> comment3 = apiService.relation(token,familyName,familyPhone);
        comment3.enqueue(new Callback<userConnect>(){

            @Override
            public void onResponse(Call<userConnect> call, Response<userConnect> response) {
                userConnect u = response.body();

                if(u.getMessage().equals("Successfully Update")){
                    Toast.makeText(getApplicationContext(), "가족 맺기 성공", Toast.LENGTH_LONG).show();
                    Intent j = new Intent(getApplicationContext(),MainActivity.class);
                    j.putExtra("로그아웃","로그아웃");
                    startActivity(j);
                    //finish();
                } else if(u.getMessage().equals("token err")){
                    Toast.makeText(getApplicationContext(), "토큰에러", Toast.LENGTH_LONG).show();
                } else if(u.getMessage().equals("No Relation")){
                    Toast.makeText(getApplicationContext(), "가족없음", Toast.LENGTH_LONG).show();
                } else if(u.getMessage().equals("Internal Server Error")){
                    Toast.makeText(getApplicationContext(), "서버에러", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<userConnect> call, Throwable t){
                Log.d("userRetrofit", t.toString());
            }
        });
    }
}