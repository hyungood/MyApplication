package com.example.user.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.user.myapplication.APIservice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listConActivity extends AppCompatActivity {
    ListView listview ;
    ListViewAdapter adapter;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    String callValue;
    Gson gson;
    Retrofit retrofit;
    APIservice apiService;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> scores =new ArrayList<>();
    ArrayList<String> contents =new ArrayList<>();
    ArrayList<Integer> successes =new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contract);
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        callValue = mPref.getString("token", "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);
        adapter = new ListViewAdapter();

//        if(callValue!=null)
        getList(callValue);
        // Adapter 생성

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);



    }

    public void getList(String token){
        Call<chkconRepo> comment = apiService.contractList(token);
        Log.d("토토토토", token);
        comment.enqueue(new Callback<chkconRepo>() {
            @Override
            public void onResponse(Call<chkconRepo> call, Response<chkconRepo> response) {
                chkconRepo p = response.body();

                if (p.getMessage().equals("Contract list ok")) {
                   for(int i=0;i<p.getMySize();i++)
                   {    names.add(p.getData().get(i).getName());
                        scores.add(p.getData().get(i).getScore());
                        contents.add(p.getData().get(i).getContent());
                        successes.add(p.getData().get(i).isSuccess());

                   }
                    for(int j=0;j<names.size();j++)
                        adapter.addItem(names.get(j), scores.get(j),contents.get(j), successes.get(j));
                        adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "내 정보 보기!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<chkconRepo> call, Throwable t) {
                Log.d("profileRetrofit", t.toString());
            }
        });

    }
    }



