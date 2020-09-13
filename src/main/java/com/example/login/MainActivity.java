package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        Info = (TextView)findViewById(R.id.tvInfo);
        userRegistration = (TextView)findViewById(R.id.tvRegister);

        Info.setText("no. of attempts left:5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user !=null) {
            finish();

            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class ));
            }
        });

    }
    private void validate(String userName, String userPassword){
        progressDialog.setMessage("You can subscribe to my channel its me sujit please wait");
        progressDialog.show();
       firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful()){
              progressDialog.dismiss();
              Toast.makeText(MainActivity.this, "login is Successful", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(MainActivity.this, SecondActivity.class));

          }else{
              Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
              counter--;
              Info.setText("No. of attempts remaining:"+ counter);
              progressDialog.dismiss();
              if(counter == 0){
                  Login.setEnabled(false);
              }
          }
           }
       });


    }
}