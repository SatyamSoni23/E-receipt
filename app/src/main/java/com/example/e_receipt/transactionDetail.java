package com.example.e_receipt;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;


//import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class transactionDetail extends AppCompatActivity {
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;

    LinearLayout invoicePage;
    EditText invoiceNo, invoiceDate, customerName, customerMobile, customerAddress, sNo, description, qty, rate, amount, total, discount, amountCustomer;
    TextView shopName, shopAddress, shopMobile, shopEmail;
    ImageView shopLogo;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        invoicePage = findViewById(R.id.invoicePage);
        invoiceNo = findViewById(R.id.invoiceNo);
        invoiceDate = findViewById(R.id.invoiceDate);
        customerName = findViewById(R.id.customerName);
        customerMobile = findViewById(R.id.customerMobile);
        customerAddress = findViewById(R.id.customerAddress);
        sNo = findViewById(R.id.sNo);
        description = findViewById(R.id.description);
        qty = findViewById(R.id.qty);
        rate = findViewById(R.id.rate);
        amount = findViewById(R.id.amount);
        total = findViewById(R.id.total);
        discount = findViewById(R.id.discount);
        amountCustomer = findViewById(R.id.amountCustomer);
        shopName = findViewById(R.id.shopName);
        shopAddress = findViewById(R.id.shopAddress);
        shopMobile = findViewById(R.id.shopMobile);
        shopEmail = findViewById(R.id.shopEmail);
        shopLogo = findViewById(R.id.shopLogo);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().toString().isEmpty()){
                    description.setError("description is empty");
                    description.requestFocus();
                    return;
                }
                if(qty.getText().toString().isEmpty()){
                    description.setError("qty is empty");
                    description.requestFocus();
                    return;
                }
                if(rate.getText().toString().isEmpty()){
                    description.setError("rate is empty");
                    description.requestFocus();
                    return;
                }
                if(amount.getText().toString().isEmpty()){
                    description.setError("amount is empty");
                    description.requestFocus();
                    return;
                }
                if(total.getText().toString().isEmpty()){
                    description.setError("total is empty");
                    description.requestFocus();
                    return;
                }
                if(amountCustomer.getText().toString().isEmpty()){
                    description.setError("Amount paid by customer is empty");
                    description.requestFocus();
                    return;
                }
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            //createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
/*
    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"HelloWorld.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(mContentEditText.getText().toString()));

        document.close();

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(docsFolder + "/HelloWorld.pdf"));
        intent.setType("application/pdf");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            startActivity(intent);
        } else {
            // Do something else here. Maybe pop up a Dialog or Toast
        }

    }*/
}
