package com.example.mypaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class first6 extends AppCompatActivity {

     PDFView firstSix;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first6);

        firstSix = (PDFView)findViewById(R.id.pdfViewF6);
        firstSix.fromAsset("First term 6.pdf").load();
    }
}
