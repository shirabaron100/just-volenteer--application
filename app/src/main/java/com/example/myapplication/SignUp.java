package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

   private TextView in_to_up;
    EditText name, email, password, phone, T_shirt_size;
    Spinner spinner;
    ArrayAdapter <CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        //המרה של הכפתור מעבר לדף כניסה
        in_to_up = findViewById(R.id.already_login);


        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Tshirt_size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((adapter));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //הפונקציה תעביר לכניסה בעזרת פונקציה פרטית
        in_to_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIN();
            }
        });
    }
        //פונקציה פרטית שתעביר מדף ההרשמה לדף הכניסה
    private void openSignIN() {
            Intent intent=new Intent(this,SignIn.class);
            startActivity(intent);
        }
    }



