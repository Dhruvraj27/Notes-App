package com.example.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    private EditText forget_password_email;
    private Button recover;
    private ImageView back;

    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

     forget_password_email=findViewById(R.id.ForgetPasswordEmail);
     recover=findViewById(R.id.recover);
     back=findViewById(R.id.back);

     firebaseAuth=FirebaseAuth.getInstance();


     back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ForgetPassword.this,LoginActivity.class);
             startActivity(intent);
         }
     });



     recover.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String mail=forget_password_email.getText().toString().trim();
             if(mail.isEmpty())
             {
                 Toast.makeText(getApplicationContext(),"Enter Your Mail First",Toast.LENGTH_SHORT).show();
             }
             else {

                 firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {

                         if (task.isSuccessful())
                         {
                             Toast.makeText(getApplicationContext(),"Mail sent,You can recover your password",Toast.LENGTH_SHORT).show();
                             finish();
                             Intent intent=new Intent(ForgetPassword.this,LoginActivity.class);
                             startActivity(intent);
                         }
                         else
                         {
                             Toast.makeText(getApplicationContext(),"Email is wrong or Account Not Exist",Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

             }
         }
     });
    }
}