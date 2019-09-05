package com.example.e_receipt;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
    public void startGooglePayActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gms.wallet.api.enabled");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
    public void startAmazonPayActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.amazon.mShop.android.shopping");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
    public void startBhimActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("upi://pay?pa=8866616231@upi&pn=Aayushi%20Shah&tn=Test%20for%20Deeplinking&am=1&cu=INR&url=https://mystar.co");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
    public void startPaytmActivity(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
        /*
        Uri uri = Uri.parse("net.one97.paytm"); // missing 'http://' will cause crashed
        //String appPackageName = "net.one97.paytm";
        Log.d(TAG, "onClick: uri: "+uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent,1);

         */
    }
}
