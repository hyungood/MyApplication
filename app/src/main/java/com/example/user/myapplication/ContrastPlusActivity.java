package com.example.user.myapplication;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ContrastPlusActivity extends AppCompatActivity {

    Button button;

    String[] items = {"할아버지", "할머니", "아빠", "엄마", "딸"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast_plus);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

    }

    public void onButton(View v) {
        Intent onButton = new Intent(this, ContractCheckActivity.class);
        startActivity(onButton);
    }

}
