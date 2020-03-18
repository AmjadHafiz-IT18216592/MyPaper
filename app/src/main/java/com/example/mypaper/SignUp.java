package com.example.mypaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseHelper mydb;

    EditText edEmail , edPassword , edConPassword ;

    Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edEmail = (EditText)findViewById(R.id.editText3);
        edPassword = (EditText)findViewById(R.id.editText4);
        edConPassword = (EditText)findViewById(R.id.editText5);
        btn = (Button) findViewById(R.id.button4);

        mydb = new DatabaseHelper(this);
        //addData();

        btn.setOnClickListener(new View.OnClickListener(){

                                   @Override
                                   public void onClick(View v) {

                                       String e1 = edEmail.getText().toString();
                                       String p = edPassword.getText().toString();
                                       String cp = edConPassword.getText().toString();

                                       if( e1.equals("")||p.equals("")||cp.equals("")){
                                           Toast.makeText(getApplicationContext(),"Fields are empty!",Toast.LENGTH_SHORT).show();
                                       }
                                       else {

                                           if(p.equals(cp)){
                                              boolean chkmail = mydb.checkEmail(e1);
                                              if(chkmail == true){

                                                   boolean isInserted =  mydb.register(e1,p);

                                                   if(isInserted == true)
                                                       Toast.makeText(getApplicationContext(),"Registered Successfully! ",Toast.LENGTH_SHORT).show();
                                               }
                                               else
                                                   Toast.makeText(getApplicationContext(),"Email already exists! ",Toast.LENGTH_SHORT).show();

                                           }
                                           else
                                               Toast.makeText(getApplicationContext(),"Passwords are not match!",Toast.LENGTH_SHORT).show();

                                       }

                                   }
                               }
        );

    }

   // public void addData(){

   // }




}
