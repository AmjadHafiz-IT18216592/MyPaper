package com.example.mypaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void open1Term(View v){
        startActivity(new Intent(this,FirstTerm.class));
    }

    public void open2Term(View v){
        startActivity(new Intent(this,SecondTerm.class));
    }

    public void open3Term(View v){
        startActivity(new Intent(this,ThirdTerm.class));
    }
}
