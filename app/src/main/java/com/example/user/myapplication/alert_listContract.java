package com.example.user.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class alert_listContract extends AppCompatActivity {
    ListView listview ;
    alert_Adapter adapter;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    String callValue;
    Gson gson;
    Retrofit retrofit;
    APIservice apiService;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> scores =new ArrayList<>();
    ArrayList<String> contents =new ArrayList<>();
    ArrayList<Integer> idxs = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contract);
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = mPref.edit();
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
        adapter = new alert_Adapter();

//        if(callValue!=null)
        getList(callValue);
        // Adapter 생성

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PromT u = (PromT)parent.getAdapter().getItem(position);
                Call<myname> comment = apiService.sendContract(callValue);
//              Log.d("토토토토", token);
                comment.enqueue(new Callback<myname>() {

                    @Override
                    public void onResponse(Call<myname> call, Response<myname> response) {
                        myname p = response.body();

                        if (p.getMessage().equals("계약서 보여주기 성공")) {
                            Intent i = new Intent(getApplicationContext(), contract_chg.class);
                            i.putExtra("nameOne", p.getData());
                            i.putExtra("nameTwo", u.getName());
                            i.putExtra("score", u.getScore());
                            i.putExtra("content", u.getContent());
                            i.putExtra("idx", u.getIdx());
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<myname> call, Throwable t) {
                        Log.d("profileRetrofit", t.toString());
                    }
                });

            }
        });




    }

    public void getList(String token){
        //token 보내면 상대방 이름 , 점수, content가 옴.
        Call<undoneCon> comment = apiService.undoneContract(token);
        Log.d("토토토토", token);
        comment.enqueue(new Callback<undoneCon>() {
            @Override
            public void onResponse(Call<undoneCon> call, Response<undoneCon> response) {
                undoneCon p = response.body();

                if (p.getMessage().equals("Undone contract list ok")) {

                    for(int i=0;i<p.getMySize();i++)
                    {    names.add(p.getData().get(i).getName());
                        scores.add(p.getData().get(i).getScore());
                        contents.add(p.getData().get(i).getContent());
                        idxs.add(p.getData().get(i).getIdx());
                    }
                    for(int j=0;j<names.size();j++)
                    { adapter.addItem(names.get(j), scores.get(j),contents.get(j), idxs.get(j));

                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "계약 보기!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<undoneCon> call, Throwable t) {
                Log.d("profileRetrofit", t.toString());
            }
        });

    }
}


