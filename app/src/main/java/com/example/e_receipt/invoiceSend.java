package com.example.e_receipt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class invoiceSend extends AppCompatActivity {

    private File pdfFile;
    Button viewPage,  send, makeAnotherInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_send);
        viewPage = findViewById(R.id.view);
        send = findViewById(R.id.send);
        makeAnotherInvoice = findViewById(R.id.makeAnotherInvoice);

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
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        pdfFile = new File(docsFolder.getAbsolutePath(),customerDetail.strCustomerName + ".pdf");
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
    public void startMakeInvoiceActivity(){
        Intent intent = new Intent(this, customerDetail.class);
        startActivity(intent);
    }
    public void startViewPageActivity(){
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        pdfFile = new File(docsFolder.getAbsolutePath(),customerDetail.strCustomerName + ".pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.valueOf(pdfFile)));
        intent.setType("application/pdf");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            startActivity(intent);
        } else {
            // Do something else here. Maybe pop up a Dialog or Toast
        }
        /*
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(pdfFile.getAbsolutePath());
        if(fileWithinMyDir.exists()) {
            Uri screenshotUri = FileProvider.getUriForFile(invoiceSend.this, getApplicationContext().getPackageName() + ".provider", pdfFile);
            intentShareFile.setType("application/pdf");
            intentShareFile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(Intent.createChooser(intentShareFile, "Open File"));
        }
         */
        /*String filename = "blabla.pdf";
        File file = new File(pdfFile.getAbsolutePath());
        Uri internal = Uri.fromFile(file);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(internal, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "U hebt geen PDF viewer geïnstalleerd op dit toestel. " +
                    "Ga naar de app store en download een PDF viewer om dit bestand te openen.", Toast.LENGTH_LONG).show();
        }
        startActivity(intent);

         */
    }
}

