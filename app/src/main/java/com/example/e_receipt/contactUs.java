package com.example.e_receipt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class contactUs extends AppCompatActivity {
    ImageView facebook, linkedIn, instagram, youTube;
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        facebook = findViewById(R.id.facebook);
        linkedIn = findViewById(R.id.linkedIn);
        instagram = findViewById(R.id.instagram);
        youTube = findViewById(R.id.youTube);
        home = findViewById(R.id.home);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFacebookActivity();
            }
        });
        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLinkedInActivity();
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInstagramActivity();
            }
        });
        youTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startYouTubeActivity();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });
    }
    public void startHomeActivity(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
    public void startFacebookActivity(){
        Intent facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
        startActivity(facebook);
    }
    public void startLinkedInActivity(){
        Intent linkedIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/feed/"));
        startActivity(linkedIn);
    }
    public void startInstagramActivity(){
        Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
        startActivity(instagram);
    }
    public void startYouTubeActivity(){
        Intent youTube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
        startActivity(youTube);
    }
}
