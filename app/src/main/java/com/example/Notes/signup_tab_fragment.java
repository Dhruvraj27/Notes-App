package com.example.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class signup_tab_fragment extends Fragment {

    private EditText signup_email,signup_password;
    private Button signup_button;


    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);


        signup_email=root.findViewById(R.id.signupemail);
        signup_password=root.findViewById(R.id.signup_password);
        signup_button=root.findViewById(R.id.signup_Button);


        firebaseAuth=FirebaseAuth.getInstance();



        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=signup_email.getText().toString().trim();
                String password=signup_password.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty() )
                {
                    Toast.makeText(getContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
                }

                else if (password.length()<7){
                    Toast.makeText(getContext(),"Password should not be greater than 7 digits",Toast.LENGTH_SHORT).show();
                }
                else {

                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful());
                            {
                                Toast.makeText(getContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                                sendEmailVerfication();
                            }


                        }

                    });
                }
            }
        });

        return root;



    }
    //send email verification

    private void sendEmailVerfication()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(),"Verification Email is Sent,Verify and Login",Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();


                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);



                }
            });
        }

        else
        {
            Toast.makeText(getContext(),"Failed to send the Verification",Toast.LENGTH_SHORT).show();

        }
    }



}

