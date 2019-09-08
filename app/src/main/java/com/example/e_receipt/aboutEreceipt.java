package com.example.e_receipt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class aboutEreceipt extends AppCompatActivity {
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ereceipt);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });
    }
    public void startHomeActivity(){
        Intent intent = new Intent(this, home.class);
        login.videoPlay = "notPlay";
        startActivity(intent);
    }
}
