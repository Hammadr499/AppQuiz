package com.example.appquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private TextView movesignin;
    private EditText Email,Pass,name,cnic,city,address;

    private FirebaseAuth firebaseAuth;
   private Button signUp;

   private FirebaseFirestore firebaseFirestore;
   DocumentReference documentReference;

   String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intialize();
    }

    public void Intialize() {
        movesignin = findViewById(R.id.TV4);
        movesignin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setMovesignin();
                return false;
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();



        Email=findViewById(R.id.Email);
        Pass=findViewById(R.id.pass);

        name=findViewById(R.id.name);
        cnic=findViewById(R.id.cnic);

        city=findViewById(R.id.city);
        address=findViewById(R.id.address);

        signUp=findViewById(R.id.LoginBT);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });
    }
    public  void  setMovesignin(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public void signupUser(){
        if(Email.getText().toString().isEmpty()&&Pass.getText().toString().isEmpty()
        &&name.getText().toString().isEmpty()&& cnic.getText().toString().isEmpty()
                &&city.getText().toString().isEmpty() &&address.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Fill All The Fields",Toast.LENGTH_SHORT).show();

        }
        else {
            final String email=Email.getText().toString().trim();
           final String password= Pass.getText().toString().trim();

           final String Fullname= name.getText().toString().trim();
            final String IDCard=cnic.getText().toString().trim();

          final String location=city.getText().toString().trim();
            final String home=address.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"User created",Toast.LENGTH_SHORT).show();
                        UserID=firebaseAuth.getCurrentUser().getUid();

                        documentReference=firebaseFirestore.collection("User").document(UserID);
                        Map<String,Object> user = new HashMap<>();

                        user.put("FullName",Fullname);
                        user.put("Email",email);

                        user.put("password",password);
                        user.put("CNIC",IDCard);

                        user.put("City",location);
                        user.put("Address",home);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Data Added",Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Data Not Added",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        FirebaseAuth.getInstance().signOut();
                        setMovesignin();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"User not created",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}