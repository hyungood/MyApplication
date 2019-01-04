package com.example.user.myapplication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SigningKeyResolver;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.String;
import java.util.List;
import java.util.Date;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.text.ParseException;

import okhttp3.ResponseBody;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class RegisterActivity extends AppCompatActivity{
    EditText ipId, ipPassword, ipName, ipPhone;
    Button register;
    TextView birthday;
    int mYear, mMonth, mDay;

    MainActivity mainActivity;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    Retrofit retrofit;
    APIservice apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myregister);
        mainActivity = new MainActivity();
        Stetho.initializeWithDefaults(this);


        birthday = (TextView)findViewById(R.id.birthday);



        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);

        mMonth = cal.get(Calendar.MONTH);

        mDay = cal.get(Calendar.DAY_OF_MONTH);

        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.

        Toolbar mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS).addNetworkInterceptor(new StethoInterceptor())
                .build();



        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();







        apiService = retrofit.create(APIservice.class);

        ipId = (EditText) findViewById(R.id.inputId);
        ipPassword = (EditText) findViewById(R.id.inputPw);

        ipName = (EditText) findViewById(R.id.inputName);
        ipPhone = (EditText) findViewById(R.id.inputPhone);
        birthday =(TextView)findViewById(R.id.birthday);
        register = (Button) findViewById(R.id.button_rg);





        // Register Button Click event
        register.setOnClickListener(new View.OnClickListener() {
            final SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
            public void onClick(View view) {

                String id = ipId.getText().toString().trim();
                int password = Integer.parseInt(ipPassword.getText().toString());
                String phone = ipPhone.getText().toString().trim();
                String name = ipName.getText().toString().trim();
                Date birth = new Date();


                Log.d("name", name);
//              Log.d("pw",password);
                Log.d("phone",phone);
                Log.d("id",id);

                try {
                    birth = transFormat.parse(birthday.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();

                }

//해야됨

                if (!(id.equals("")) && ((int)(Math.log10(password)+1))>0 && !(phone.equals(""))&& !(name.equals(""))&& birth!=null){
                    Log.d("접속",id);
                    registerUser(name, id, password, phone, birth);
                    Log.d("접속",id);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "모든 회원정보를 입력해주세요.", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    };
    private static void disableSSLCertificateChecking() {

        System.out.println("INSIDE DISABLE SSLC");
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void mOnClick(View v){

        switch(v.getId()) {

            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌

            case R.id.btnchangedate: {
//날짜 대화상자 리스너 부분

                DatePickerDialog.OnDateSetListener mDateSetListener =

                        new DatePickerDialog.OnDateSetListener() {


                            @Override

                            public void onDateSet(DatePicker view, int year, int monthOfYear,

                                                  int dayOfMonth) {

                                // TODO Auto-generated method stub

                                //사용자가 입력한 값을 가져온뒤
                                mYear = year;

                                mMonth = monthOfYear;

                                mDay = dayOfMonth;

                                //텍스트뷰의 값을 업데이트함

                                UpdateNow();

                            }

                        };
                //여기서 리스너도 등록함

                new DatePickerDialog(RegisterActivity.this, R.style.DialogTheme, mDateSetListener, mYear,

                        mMonth, mDay).show();

                break;
            }

            case R.id.chkid:{
                String id = ipId.getText().toString().trim();
                if(id.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요",Toast.LENGTH_LONG).show();
                }
                else{
                    Call<userConnect> comment2 = apiService.chkId(id);
                    Log.d("시도","id");
                    comment2.enqueue(new Callback<userConnect>() {

                        @Override
                        public void onResponse(Call<userConnect> call, Response<userConnect> response) {
//                        Log.v("Test", "Response Body is" + response.body().toString());
                            disableSSLCertificateChecking();
                            userConnect u = response.body();

                            if( u.getMessage().equals("Already Joined")) {
                                Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_LONG).show();
                            }
                            else if(u.getMessage().equals("Can use your ID")){
                                Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다", Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<userConnect> call, Throwable t) {
                            Log.d("userRetrofit", t.toString());
                        }


                    });
                }

            }
            default:
                break;
        }
    }



    void UpdateNow(){

        birthday.setText(String.format("%d/%d/%d", mYear,

                mMonth + 1, mDay));}


    private void registerUser(final String name,final String id, final int password, final String phone, final Date date) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        Call<userConnect> comment2 = apiService.register(name, id, password, phone, date);
        comment2.enqueue(new Callback<userConnect>() {
            @Override
            public void onResponse(Call<userConnect> call, Response<userConnect> response) {
                userConnect u = response.body();
                if( u.getMessage().equals("Successfully signed up")) {
//                    finish(); //다시 메인화면으로 돌아가기
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_LONG).show();
                    mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.clear();
                    editor.putString("token",u.getToken());
                    editor.commit();

                    String t = u.getToken();
                    Intent i = new Intent(getApplicationContext(), RegisterF.class);
                    i.putExtra("token", t);
                    startActivity(i);


                }
                else if(u.getMessage().equals("Internal server Error")){
                    Toast.makeText(getApplicationContext(), "내부 서버 오류", Toast.LENGTH_LONG).show();

                }
                else if(u.getMessage().equals("Null Value")){
                    Toast.makeText(getApplicationContext(), "널값", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<userConnect> call, Throwable t) {
                Log.d("userRetrofit", t.toString());
            }


        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작

                finish();
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void onDestroy() {
        super.onDestroy();
        editor = mPref.edit();
        editor.remove("token");
        editor.commit();
    }
}

