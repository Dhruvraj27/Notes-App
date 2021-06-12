package com.example.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class                                                                                                                                                                                                                         CreateNote extends AppCompatActivity {

    EditText createtitle,createcontent;
    FloatingActionButton savenote;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        savenote=findViewById(R.id.SaveFab);
        createtitle=findViewById(R.id.createtitleofnote);
        createcontent=findViewById(R.id.createcontent);

        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

     savenote.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String title=createtitle.getText().toString();
             String content=createcontent.getText().toString();
             if(title.isEmpty()||content.isEmpty())
             {
                 Toast.makeText(getApplicationContext(),"Both Field are Required",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("MyNotes").document();
                 Map<String,Object> note=new HashMap<>();
                 note.put("title",title);
                 note.put("content",content);

                 documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {

                         Toast.makeText(getApplicationContext(),"Note Created Successfully",Toast.LENGTH_SHORT).show();
                         Intent intent=new Intent(CreateNote.this,MainActivity.class);
                         startActivity(intent);
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getApplicationContext(),"Failed to Create Note",Toast.LENGTH_SHORT).show();

                     }
                 });

             }

         }
     });






    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }
}