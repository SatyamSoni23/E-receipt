package com.example.e_receipt;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class invoiceSend extends AppCompatActivity {

    private File pdfFile;
    Button viewPage,  send, makeAnotherInvoice;
    String dirpath;
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
}

