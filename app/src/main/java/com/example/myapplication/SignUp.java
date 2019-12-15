package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private TextView in_to_up;
    EditText mFullName, mEmail, mPassword, mPhone, T_shirt_size;
    Button mRegisssterBtn;
    TextView mLginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    // DB
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedIdInstanceState) {
        super.onCreate(savedIdInstanceState);
        setContentView(R.layout.sign_up);

        // Init db
        database = FirebaseDatabase.getInstance();

        mFullName = findViewById(R.id.name_up);
        mEmail = findViewById(R.id.email_IN_up);
        mPassword = findViewById(R.id.password_IN_up);
        //mPhone= findViewById(R.id.);
        mRegisssterBtn = findViewById(R.id.register_now);
        mLginBtn = findViewById(R.id.already_login);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), SignIn.class));
//            finish();
//        }

        mLginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIN();
            }
        });

        mRegisssterBtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  String email = mEmail.getText().toString().trim();
                                                  String password = mPassword.getText().toString().trim();

                                                  if (TextUtils.isEmpty(email)) {
                                                      mEmail.setError("Email is Requierd.");
                                                      return;
                                                  }

                                                  if (TextUtils.isEmpty(password)) {
                                                      mPassword.setError("password is Requierd.");
                                                      return;
                                                  }
                                                  if (password.length() < 6) {
                                                      mPassword.setError("Password Must be>= 6 Characters");
                                                      return;
                                                  }

                                                  progressBar.setVisibility(view.VISIBLE);

                                                  fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                                          if (task.isSuccessful()) {
                                                              Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                                              startActivity(new Intent(getApplicationContext(), SignIn.class));

                                                              User user = new User(mEmail.getText().toString(), mFullName.getText().toString());

                                                              String uid = task.getResult().getUser().getUid();

                                                              myRef = database.getReference("users/" + uid);

                                                              myRef.setValue(user);

                                                          } else {
                                                              Toast.makeText(SignUp.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                                  });

                                              }

                                          }

        );
    }

    private void openSignIN() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

}
















//package com.example.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.myapplication.models.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class SignUp extends AppCompatActivity {
//
//    private TextView in_to_up;
//    EditText mFullName, mEmail, mPassword, mPhone, T_shirt_size;
//    Button mRegisssterBtn;
//    TextView mLginBtn;
//    FirebaseAuth fAuth;
//    ProgressBar progressBar;
//    Spinner spinner;
//    ArrayAdapter <CharSequence> adapter;
//
//    // DB
//    FirebaseDatabase database;
//    DatabaseReference myRef;
//
//    @Override
//    protected void onCreate(Bundle savedIdInstanceState){
//        super.onCreate(savedIdInstanceState);
//        setContentView(R.layout.sign_up);
//        Log.i("sign up", "Sign up..");
//        System.out.println("sign up");
//
//        // Init db
//        database = FirebaseDatabase.getInstance();
//
//        mFullName =findViewById(R.id.name);
//        mEmail= findViewById(R.id.email_IN);
//        mPassword= findViewById(R.id.password_IN);
//        //mPhone= findViewById(R.id.);
//        mRegisssterBtn= findViewById(R.id.register_now);
//        mLginBtn= findViewById(R.id.already_login);
//        fAuth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressBar2);
//
//        if(fAuth.getCurrentUser() != null ){
//            startActivity(new Intent(getApplicationContext(),SignIn.class));
//            finish();
//        }
//
//        mRegisssterBtn.setOnClickListener(new View.OnClickListener() {
//                                              @Override
//                                              public void onClick(View view) {
//              String email= mEmail.getText().toString().trim();
//              String password = mPassword.getText().toString().trim();
//
//              if(TextUtils.isEmpty(email)){
//                  mEmail.setError("Email is Requierd.");
//                  return;
//              }
//
//              if(TextUtils.isEmpty(password)){
//                  mPassword.setError("password is Requierd.");
//                  return;
//              }
//              if(password.length()<6){
//                  mPassword.setError("Password Must be>= 6 Characters");
//                  return;
//              }
//
//              progressBar.setVisibility(view.VISIBLE);
//
//              fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                  @Override
//                  public void onComplete(@NonNull Task<AuthResult> task) {
//                      if(task.isSuccessful()){
//                          Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
//                          startActivity(new Intent(getApplicationContext(),SignIn.class));
//
//                          User user = new User(mEmail.getText().toString(), mFullName.getText().toString());
//
//                          String uid = task.getResult().getUser().getUid();
//
//                          myRef = database.getReference("users/" + uid);
//
//                          myRef.setValue(user);
//
//                      }else{
//                          Toast.makeText(SignUp.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                      }
//                  }
//              });
//
//          }
//
//      }
//
//        );
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//   /*EditText name, email, password, phone, T_shirt_size;
//    Spinner spinner;
//    ArrayAdapter <CharSequence> adapter;
//    FirebaseAuth fAuth;
//    Button mRegisterBtn;
//    ProgressBar progressBar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sign_up);
//        //המרה של הכפתור מעבר לדף כניסה
//        in_to_up = findViewById(R.id.already_login);
//        spinner = (Spinner) findViewById(R.id.spinner);
//        adapter = ArrayAdapter.createFromResource(this, R.array.Tshirt_size, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter((adapter));
//        fAuth=FirebaseAuth.getInstance();
//        mRegisterBtn= findViewById(R.id.register_now);
//        //progressBar = findViewById(R.id.progresBar);
//        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             String Email=email.getText().toString().toString().trim();
//             String Password=password.getText().toString().trim();
//             if(TextUtils.isEmpty(Email)){
//                 email.setError("Emaili is Required.");
//                 return;
//             }
//             if(TextUtils.isEmpty(Password)){
//                 password.setError("Emaili is Required.");
//                 return;
//             }
//             if(password.length()<6){
//               password.setError("password Must be >= 6 characters");
//               return;
//             }
//                progressBar.setVisibility(view.VISIBLE);
//            }
//            fAuth.createUserWithEmailAndPassword(Email,password).addOncompleteL
//        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        //הפונקציה תעביר לכניסה בעזרת פונקציה פרטית
//        in_to_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSignIN();
//            }
//        });
//    }
//        //פונקציה פרטית שתעביר מדף ההרשמה לדף הכניסה
//    private void openSignIN() {
//            Intent intent=new Intent(this,SignIn.class);
//            startActivity(intent);
//        }*/
//}
