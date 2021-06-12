package com.example.Notes;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_tab_fragment extends Fragment  {


    EditText loginemail,loginpassword;
    Button loginButton;
    TextView forgetpassword;
    float v;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        loginemail=root.findViewById(R.id.loginemail);
        loginpassword=root.findViewById(R.id.loginpassword);
        forgetpassword=root.findViewById(R.id.forgetpassword);
        loginButton=root.findViewById(R.id.loginButton);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        loginemail.setTranslationX(800);
        loginpassword.setTranslationX(800);
        forgetpassword.setTranslationX(800);
        loginButton.setTranslationX(800);


        loginemail.setAlpha(v);
        loginpassword.setAlpha(v);
        forgetpassword.setAlpha(v);
        loginButton.setAlpha(v);


        loginemail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        loginpassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetpassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),ForgetPassword.class);
                startActivity(intent);
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=loginemail.getText().toString().trim();
                String password=loginpassword.getText().toString().trim();


                if (mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getContext(),"All Field are Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful())
                           {
                               checkmailverfication();
                           }
                           else
                           {
                               Toast.makeText(getContext(),"Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                }
            }
        });


        return root;



    }
    private  void checkmailverfication()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified())
        {
            Toast.makeText(getContext(),"Logged In",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getContext(),"Verify Your Email First",Toast.LENGTH_SHORT).show();
        }
        
    }
}
