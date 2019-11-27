package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class SignIn extends AppCompatActivity {
        TextView to_log_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        to_log_up=findViewById(R.id.sign_up_now);
        to_log_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUP();
            }
        });
    }
    //פונקציה פרטית שתעביר מדף ההרשמה לדף הכניסה
    private void openSignUP() {
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}

