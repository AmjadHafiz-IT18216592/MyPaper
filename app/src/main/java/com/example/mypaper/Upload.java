package com.example.mypaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Upload extends AppCompatActivity {

    Button selectFile , upload;
    TextView notification;
    RadioGroup rg;
    RadioButton T1 ,T2 ,T3;
    EditText grade;
    Uri pdfUri;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        rg = (RadioGroup) findViewById(R.id.rg);
        T1 = (RadioButton) findViewById(R.id.T1);
        grade = (EditText) findViewById(R.id.grade);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        selectFile = findViewById(R.id.selectFile);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.notification);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(Upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pdfUri != null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(Upload.this, "Please select File!", Toast.LENGTH_SHORT).show();
            }
        });
 /*
            int g = Integer.parseInt(grade.getText().toString());
            public void onClick(View view) {
                if( T1 == null||grade == null){
                    Toast.makeText(getApplicationContext(),"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if((g< 6) || (g > 11)){
                    Toast.makeText(getApplicationContext(),"Grade should be 6 to 11",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pdfUri != null){
                        uploadFile(pdfUri);
                    }

                    else
                        Toast.makeText(Upload.this,"Please select File!",Toast.LENGTH_SHORT).show();

                }     }
             */

    }

    public void uploadFile(Uri pdfUri){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File......");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference= storage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String url = taskSnapshot.getUploadSessionUri().toString();
                    //getDownloadUrl()
                DatabaseReference reference = database.getReference();
            reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                        Toast.makeText(Upload.this,"File Uploaded!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Upload.this,"File not Uploaded!",Toast.LENGTH_SHORT).show();

                }
            });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Upload.this,"File not Uploaded!",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
            progressDialog.setProgress(currentProgress);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else
            Toast.makeText(Upload.this,"Please provide permission",Toast.LENGTH_SHORT).show();
    }

    public void selectPdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.setText("A File selected " + data.getData().getLastPathSegment());
            //Toast.makeText(Upload.this, "File selected!", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(Upload.this, "Please select a File!", Toast.LENGTH_SHORT).show();

    }



}
