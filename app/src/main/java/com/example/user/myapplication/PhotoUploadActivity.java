package com.example.user.myapplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class PhotoUploadActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);


        ImageButton openimg = (ImageButton) findViewById(R.id.btn_UploadPicture);
        openimg.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivity(intent);
            }
        });

    }

    public void onButton1Clicked(View v) {
        Toast.makeText(this, "퍼즐 게임에 사용되는 사진을 골라주세요!", Toast.LENGTH_LONG).show();
    }
}



