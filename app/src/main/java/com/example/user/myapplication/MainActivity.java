package com.example.user.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button login;
    SharedPreferences mPref;
    String callValue;
    SharedPreferences.Editor editor;
//    SessionManager session;


    public MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        callValue = mPref.getString("token", "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
//        FirebaseInstanceId.getInstance().getToken();
//
//        if (FirebaseInstanceId.getInstance().getToken() != null) {
//            Log.d("tag", "token = " + FirebaseInstanceId.getInstance().getToken());
//        }
//        session = new SessionManager(getApplicationContext());


        int type;
        ImageButton btn1 = (ImageButton)findViewById(R.id.puzzle);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), puzzleActivity.class);
                startActivity(intent);

            }
        });
        ImageButton btn2 = (ImageButton)findViewById(R.id.tetris);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tetris.class);
                startActivity(intent);

            }
        });
        ImageButton btn3 = (ImageButton)findViewById(R.id.matching);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), matchActivity.class);
                startActivity(intent);
            }
        });
        ImageButton btn4 = (ImageButton)findViewById(R.id.ordering);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RandomNumberActivity.class);
                startActivity(intent);
            }
        });


        ImageButton btn5= (ImageButton)findViewById(R.id.contract);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContractActivity.class);
                startActivity(intent);

            }
        });
        ImageButton btn6 = (ImageButton)findViewById(R.id.gallery);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoUploadActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View headerview = navigationView.getHeaderView(0);

        login = (Button)headerview.findViewById(R.id.login);
        Log.e("로그인 이름", login.getText().toString());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString().equals("로그인")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    editor = mPref.edit();
                    editor.remove("token");
                    editor.commit();
                    login.setText("로그인");
                }
            }
        });
        Intent i = getIntent();
        String logout = i.getStringExtra("로그아웃");
        final int num = i.getIntExtra("num",1);
        if(logout!=null)
            login.setText("로그아웃");
        else
            login.setText("로그인");

    }

    public void onDestroy() {
        super.onDestroy();
        editor = mPref.edit();
        editor.remove("token");
        editor.commit();
    }
//    public void onPause() {
//        super.onPause();
//        editor = mPref.edit();
//        editor.clear();
//        editor.commit();
//    }

//    public static void changeBtn(SessionManager session){
//        if (session.isLoggedIn()) {
//            login.setVisibility(View.INVISIBLE);
//        }
//    }



//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myPage) {
//            if(!session.isLoggedIn()) {
//                Toast.makeText(getApplicationContext(), "로그인부터 해주세요~", Toast.LENGTH_LONG).show();
//            }
//            else {
            Intent i = new Intent(getApplicationContext(), activity_mypage.class);
            startActivity(i);
//                Toast.makeText(getApplicationContext(), "출력할 문자열", Toast.LENGTH_LONG).show();
//            drawer.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
//                startActivity(intent);
//            }

        }

        return true;
    }
}