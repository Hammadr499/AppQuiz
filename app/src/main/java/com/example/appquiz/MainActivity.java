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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView signup;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private EditText email,password;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
intialize();
    }
    public void intialize() {
        signup = findViewById(R.id.TV4);
        signup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moveToSignUP();
                return false;
            }
        });

        email=findViewById(R.id.Email);
        password=findViewById(R.id.pass);


    }
    public  void moveToSignUP(){
        Intent i = new Intent(getApplicationContext(),SignUp.class);
        startActivity(i);
    }
public void signIn(){
        String Email= email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(Email.isEmpty()&&pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Fill All The Fields",Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(),Upload.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}

}