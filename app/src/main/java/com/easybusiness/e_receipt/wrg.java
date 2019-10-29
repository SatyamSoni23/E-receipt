package com.easybusiness.e_receipt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class wrg extends AppCompatActivity {
    Button tryAgain, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrg);

        tryAgain = findViewById(R.id.tryAgain);
        signup = findViewById(R.id.signup);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTryAgainActivity();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignupActivity();
            }
        });
    }
    public void startTryAgainActivity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
    public void startSignupActivity(){
        Intent intent = new Intent(this, registerPage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        startLoginActivity();
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
