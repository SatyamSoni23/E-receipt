package com.example.e_receipt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
