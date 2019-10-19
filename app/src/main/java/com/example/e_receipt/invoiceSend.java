package com.example.e_receipt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class invoiceSend extends AppCompatActivity {

    private File pdfFile;
    Button viewPage,  send, makeAnotherInvoice;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_send);
        viewPage = findViewById(R.id.view);
        send = findViewById(R.id.send);
        makeAnotherInvoice = findViewById(R.id.makeAnotherInvoice);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile();
            }
        });
        makeAnotherInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMakeInvoiceActivity();
            }
        });

        viewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewPageActivity();
            }
        });
    }
    public void shareFile(){
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/E-Receipt");
        pdfFile = new File(docsFolder.getAbsolutePath(), transactionDetail.count + " - " + customerDetail.strCustomerName + ".pdf");
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(pdfFile.getAbsolutePath());
        if(fileWithinMyDir.exists()) {
            Uri screenshotUri = FileProvider.getUriForFile(invoiceSend.this, getApplicationContext().getPackageName() + ".provider", pdfFile);
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM,screenshotUri);
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }
    public void startHomeActivity(){
        Intent intent = new Intent(this, home.class);
        login.videoPlay = "notPlay";
        startActivity(intent);
    }
    public void startMakeInvoiceActivity(){
        Intent intent = new Intent(this, customerDetail.class);
        startActivity(intent);
    }
    public void startViewPageActivity(){
        Intent intent = new Intent(this, view.class);
        startActivity(intent);
    }
}

