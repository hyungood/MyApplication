package com.example.user.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContractActivity extends AppCompatActivity {


    SharedPreferences mPref;
    Button button;
    Retrofit retrofit;
    APIservice apiService;
    String[] a={"","","","","",""};

    EditText editText2, editText3;
    int score;
    String content, nameM, nameU;
    String callValue;
    Spinner spinner, spinner2;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
//
//

            adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, a);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
            );
            spinner.setAdapter(adapter);
            spinner2.setAdapter(adapter);

            getMotion();
    }


    public void getMotion(){

        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        callValue = mPref.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIservice.class);
        if (callValue == "") {
            Toast.makeText(getApplicationContext(), "먼저 로그인을 하세요", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(callValue !=null){
        Call<Repo> comment = apiService.postToken(callValue);
        comment.enqueue(new Callback<Repo>() {

            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Repo u = response.body();

                for (int i = 0; i < u.getSize(); i++) {
                    a[i] = u.getData().get(i).getName();
                    adapter.notifyDataSetChanged();
                }


                try {
                    Log.e("이름들", u.getData().get(0).getName());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                if (u.getMessage().equals("Successfully Update")) {
                    Toast.makeText(getApplicationContext(), "이름 업데이트 완료", Toast.LENGTH_LONG).show();
                } else if (u.getMessage().equals("No Relation")) {
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.d("userRetrofit", t.toString());
            }


        });
    }
    }

   public void onButtonClicked(View v) {
        Intent onButton = new Intent(this, ContrastPlusActivity.class);
        startActivity(onButton);
   }

    public void onButton(View v) {
        score = Integer.parseInt(editText2.getText().toString());
        content = editText3.getText().toString();
        nameM = spinner.getSelectedItem().toString();
        nameU = spinner2.getSelectedItem().toString();
        if(!(editText2.equals("")) && !(content.equals("")) &&!(nameM.equals(""))&&!(nameU.equals("")))
        {
            Call<userConnect> comment = apiService.writeCon(callValue, nameM, nameU, score, content);
            comment.enqueue(new Callback<userConnect>() {
            @Override
            public void onResponse(Call<userConnect> call, Response<userConnect> response) {
                userConnect u = response.body();

                if( u.getMessage().equals("Successfully made the contract")) {
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




