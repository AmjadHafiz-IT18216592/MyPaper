package com.example.mypaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }
    public void openB(View v){
        startActivity(new Intent(ActivityA.this,ActivityB.class));
    }

    public void openUpload(View v){
        startActivity(new Intent(ActivityA.this,Upload.class));
    }

}
