package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import android.widget.Toast;
import android.text.TextUtils;

public class SignIn extends AppCompatActivity {
    TextView to_log_up;
    Button log_in;

    private FirebaseAuth auth;

    private EditText emailET;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        to_log_up=findViewById(R.id.sign_up_now);
        log_in = findViewById(R.id.sign_in);

        emailET = findViewById(R.id.email_IN);
        passwordET = findViewById(R.id.password_IN);

        auth = FirebaseAuth.getInstance();

        to_log_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSignUP();
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignIn.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }

                else {

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                                logIn();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_SHORT).show();
//                                return;
                            }
                        }
                    });

                }


            }
        });

    }
    //פונקציה פרטית שתעביר מדף ההרשמה לדף הכניסה
    private void openSignUP() {
        Intent intent=new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //log in
    private void logIn() {
        Intent intent=new Intent(this, MainActivity.class);
//        Log.i("activity main", "main activity..");
        startActivity(intent);
    }
}
