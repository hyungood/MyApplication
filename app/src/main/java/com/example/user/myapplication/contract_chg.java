package com.example.user.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class contract_chg extends AppCompatActivity {
    SharedPreferences mPref;
    Button button;
    Retrofit retrofit;
    APIservice apiService;
    int idxs;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    EditText editText2, editText3;
    int score;
    String content, nameM, nameU;
    String callValue;
    TextView nameOne, nameTwo;
    ArrayAdapter<String> adapter;
    String idxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_chg);

        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        nameOne= (TextView)findViewById(R.id.nameOne);
        nameTwo =(TextView)findViewById(R.id.nameTwo);

       btn1 =(Button)findViewById(R.id.button1);
       btn2 =(Button)findViewById(R.id.button2);
       btn3 =(Button)findViewById(R.id.button3);
        btn4 =(Button)findViewById(R.id.button4);

         btn5 =(Button)findViewById(R.id.button5);
         btn6 =(Button)findViewById(R.id.button6);
         btn7 =(Button)findViewById(R.id.button7);
         btn8 =(Button)findViewById(R.id.button8);

        Intent i = getIntent();
        String nameo = i.getStringExtra("nameOne");
        String namet = i.getStringExtra("nameTwo");
        score = i.getIntExtra("score", 0);
        String content = i.getStringExtra("content");
        idxs = i.getIntExtra("idx", 0);

//       인텐트로 값 넘겨받아서 이름 적기, 계약 내용적기..
        nameOne.setText(nameo);
        nameTwo.setText(namet);

        editText2.setText(Integer.toString(score));
        editText3.setText(content);


        chkColor(score);
        chkColorTwo(content);

        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        callValue = mPref.getString("token", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIservice.class);
    }

    public void chkColor(int score){
        switch(score){
            case 500:
                btn1.setTextColor(Color.RED);
                break;
            case 1000:
                btn2.setTextColor(Color.RED);
                break;
            case 1500:
                btn3.setTextColor(Color.RED);
                break;
            case 2000:
                btn4.setTextColor(Color.RED);
                break;

                default:
                    break;

    }
    }

    public void chkColorTwo(String content){
        if(content.equals("전화"))
            btn5.setTextColor(Color.RED);
        else if(content.equals("편지"))
            btn6.setTextColor(Color.RED);
        else if(content.equals("방문"))
            btn7.setTextColor(Color.RED);
        else if(content.equals("문자"))
            btn8.setTextColor(Color.RED);


    }



    public void onButtonClicked(View v) {
        Intent onButton = new Intent(this, ContrastPlusActivity.class);
        startActivity(onButton);
    }

    //다시 계약 보내는 페이지
    public void onButton(View v) {
        score = Integer.parseInt(editText2.getText().toString());
        content = editText3.getText().toString();
        nameM = nameOne.getText().toString();
        nameU = nameTwo.getText().toString();
        Log.e("테스트텥~", "idxs입니다: " + idxs);
//        idxs = Integer.parseInt(idxx);
        if (!(editText2.equals("")) && !(content.equals("")) && !(nameM.equals("")) && !(nameU.equals(""))) {
            Call<userConnect> comment = apiService.modifyContract(callValue, idxs, score, content);
            comment.enqueue(new Callback<userConnect>() {
                @Override
                public void onResponse(Call<userConnect> call, Response<userConnect> response) {
                    userConnect u = response.body();

                    if(u.getMessage().equals("계약 최종 완료")) {
                        Toast.makeText(getApplicationContext(), "약속 맺기 성공", Toast.LENGTH_LONG).show();
                        Intent onButton = new Intent(getApplicationContext(), ContractCheckActivity.class);
                        startActivity(onButton);
                    }
                    else if(u.getMessage().equals("Null Value")){
                        Toast.makeText(getApplicationContext(), "본인의 이름을 필수로 선택해야 합니다.", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<userConnect> call, Throwable t) {
                    Log.d("userRetrofit", t.toString());
                }


            });

        }
        else{
            Toast.makeText(getApplicationContext(), "모두다 입력해주세요", Toast.LENGTH_LONG).show();
        }
    }




    public void clicked(View v){
        switch(v.getId()) {
            case R.id.button1:
                editText2.setText("500");
                break;
            case R.id.button2:
                editText2.setText("1000");
                break;
            case R.id.button3:
                editText2.setText("1500");
                break;
            case R.id.button4:
                editText2.setText("2000");
                break;
            default:
                break;

        }
    }
    public void clicked2(View v){
        switch(v.getId()) {
            case R.id.button5:
                editText3.setText("전화");
                break;
            case R.id.button6:
                editText3.setText("편지");
                break;
            case R.id.button7:
                editText3.setText("방문");
                break;
            case R.id.button8:
                editText3.setText("문자");
                break;
            default:
                break;

        }
    }

}



