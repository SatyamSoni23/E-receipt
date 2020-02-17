package com.easybusiness.e_receipt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    Button login, register;
    DatabaseHelper myDb;
    private FirebaseAuth mAuth;
    public static  int  flag = 0;
    public static String currentEmail;

    @Override
    public void onStart() {
        super.onStart();
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Database Helper And Firebase Helper xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedPreferences.getString("LOGIN", null);
        if(login != null){
            startInternalActivity();
            flag = 2;
        }
        else{
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                currentEmail = currentUser.getEmail();
                flag = 1;
                startInternalActivity();
            }
        }
    }
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeAcitvity();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
        mAuth = FirebaseAuth.getInstance();


    }
    public void startInternalActivity(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void startHomeAcitvity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
    public void startRegisterActivity(){
        Intent intent = new Intent(this, registerPage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Login or Register your shop", Toast.LENGTH_SHORT).show();
    }
}
