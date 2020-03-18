package com.example.mypaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper mydb;

    EditText edEmail , edPassword  ;

    Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        log();
        }


    public void openSignUp(View v) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void log(){
        edEmail = (EditText)findViewById(R.id.editText);
        edPassword = (EditText)findViewById(R.id.editText2);

        btn = (Button) findViewById(R.id.button);

        mydb = new DatabaseHelper(this);
        //addData();

        btn.setOnClickListener(new View.OnClickListener(){

                                   @Override
                                   public void onClick(View v) {

                                       String e1 = edEmail.getText().toString();
                                       String p = edPassword.getText().toString();

                                       if( e1.equals("")||p.equals("")){
                                           Toast.makeText(getApplicationContext(),"Fields are empty!",Toast.LENGTH_SHORT).show();
                                       }
                                       else {
                                           boolean chkmail = mydb.checkEmail(e1);
                                           if(chkmail == false) {


                                               boolean result = mydb.login(e1, p);
                                               if (result == true) {
                                                   Intent intent = new Intent(MainActivity.this, ActivityA.class);
                                                   startActivity(intent);

                                               } else
                                                   Toast.makeText(getApplicationContext(), "Password not match!", Toast.LENGTH_SHORT).show();
                                           }
                                           else
                                               Toast.makeText(getApplicationContext(), "Incorrect Email!", Toast.LENGTH_SHORT).show();

                                       }

                                   }
                               }
        );
    }
}
