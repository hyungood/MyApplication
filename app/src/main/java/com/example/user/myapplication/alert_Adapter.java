package com.example.user.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class alert_Adapter extends BaseAdapter {
    Retrofit retrofit;
    APIservice apiService ;
    Gson gson;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(30000, TimeUnit.SECONDS)
            .build();


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<PromT> listViewItemList = new ArrayList<PromT>() ;


    // ListViewAdapter의 생성자
    public alert_Adapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);

        final int pos = position;
        final Context context = parent.getContext();
        int i=1;
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_two, parent, false);

        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView id= (TextView) convertView.findViewById(R.id.textView0) ;
        TextView score = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView content = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView foridx = (TextView)convertView.findViewById(R.id.foridx);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        PromT listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        id.setText(listViewItem.getName());
        score.setText(listViewItem.getScore()+"");
        content.setText(listViewItem.getContent());
        foridx.setText(listViewItem.getIdx()+"");


        convertView.setMinimumHeight(250);

        //서버와 통신하여 내 이름 받기
        //id, content 다 인텐트로 다음 페이지에 넘겨주기.
        //contract_chg로 넘기기.
        return convertView;



    }

//    public void getList() {
//        //token 보내면 상대방 이름 , 점수, content가 옴.
//
//        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        editor = mPref.edit();
//
//        Call<undoneCon> comment = apiService.sendContract(token);
//        Log.d("토토토토", token);
//        comment.enqueue(new Callback<undoneCon>() {
//            @Override
//            public void onResponse(Call<undoneCon> call, Response<undoneCon> response) {
//                undoneCon p = response.body();
//
//                if (p.getMessage().equals("Undone contract list ok")) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<undoneCon> call, Throwable t) {
//                Log.d("profileRetrofit", t.toString());
//            }
//        });
//    }
    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, int score, String content, int idx) {
        PromT item = new PromT();

        item.setName(name);
        item.setScore(score);
        item.setContent(content);
        item.setIdx(idx);

        listViewItemList.add(item);
    }
}
