package com.example.e_receipt;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class transferMoney extends AppCompatActivity {
    ImageView phonePe, googlePay, amazonPay, bhim, paytm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        phonePe = findViewById(R.id.phonePe);
        googlePay = findViewById(R.id.googlePay);
        amazonPay = findViewById(R.id.amazonPay);
        bhim = findViewById(R.id.bhim);
        paytm = findViewById(R.id.paytm);
        phonePe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhonePeActivity();
            }
        });
        googlePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGooglePayActivity();
            }
        });
        amazonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAmazonPayActivity();
            }
        });
        bhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBhimActivity();
            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaytmActivity();
            }
        });
    }
    public void startPhonePeActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.phonepe.app");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }
    public void startGooglePayActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }
    public void startAmazonPayActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.amazon.mShop.android.shopping");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }
    public void startBhimActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.org.npci.upiapp");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }
    public void startPaytmActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
        else{
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
