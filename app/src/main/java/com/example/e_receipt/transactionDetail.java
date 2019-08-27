package com.example.e_receipt;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class transactionDetail extends AppCompatActivity {
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    DatabaseReference rootRef, demoRef;
    private StorageReference storageRef, dataRef;
    private RelativeLayout invoicePage;
    EditText invoiceNo, invoiceDate, sNo, description, qty, rate, amount, total, discount, amountCustomer;
    TextView shopName,customerName, customerMobile, customerAddress, shopAddress, shopMobile, shopEmail, gstNumber;
    ImageView shopLogo;
    private Button save;
    String dirpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        invoicePage = (RelativeLayout)findViewById(R.id.invoicePage);
        invoiceNo = (EditText) findViewById(R.id.invoiceNo);
        invoiceDate = (EditText)findViewById(R.id.invoiceDate);
        customerName = (TextView) findViewById(R.id.customerName);
        customerMobile = (TextView) findViewById(R.id.customerMobile);
        customerAddress = (TextView) findViewById(R.id.customerAddress);
        sNo = (EditText)findViewById(R.id.sNo);
        description = (EditText)findViewById(R.id.description);
        qty = (EditText)findViewById(R.id.qty);
        rate = (EditText)findViewById(R.id.rate);
        amount = (EditText)findViewById(R.id.amount);
        total = (EditText)findViewById(R.id.total);
        discount = (EditText)findViewById(R.id.discount);
        amountCustomer = (EditText)findViewById(R.id.amountCustomer);
        shopName = (TextView)findViewById(R.id.shopName);
        shopAddress = (TextView)findViewById(R.id.shopAddress);
        shopMobile = (TextView)findViewById(R.id.shopMobile);
        shopEmail = (TextView)findViewById(R.id.shopEmail);
        gstNumber = (TextView)findViewById(R.id.gstNumber);
        shopLogo = (ImageView)findViewById(R.id.shopLogo);
        save = (Button) findViewById(R.id.save);

        customerName.setText(customerDetail.strCustomerName);
        customerMobile.setText(customerDetail.strCustomerMobile);
        customerAddress.setText(customerDetail.strCustomerAddress + " " + customerDetail.strCustomerPincode);

        storageRef = FirebaseStorage.getInstance().getReference().child("shopLogo").child(login.strUsername);
        dataRef = storageRef.child(login.strUsername + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(transactionDetail.this).load(uri).into(shopLogo);
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername).child("shopDetail");
        demoRef.child("shopName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopName.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });
        demoRef.child("shopAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopAddress.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });
        demoRef.child("shopMobile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopMobile.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });

        demoRef.child("shopEmail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopEmail.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });
        demoRef.child("gstNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gstNumber.setText("Invoice - GST Number : " + dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });


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

                layoutToImage();
                imageToPDF();
            }
        });
    }
    public void layoutToImage() {
        invoicePage.setDrawingCacheEnabled(true);
        invoicePage.buildDrawingCache();
        Bitmap bm = invoicePage.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


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
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void imageToPDF() {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath +"/Documents" + "/" + customerDetail.strCustomerName + ".pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
            startSaveActivity();
        } catch (Exception e) {

        }
    }
    public void startSaveActivity(){
        Intent intent = new Intent(this, invoiceSend.class);
        startActivity(intent);
    }
    public void startErrorActivity(){
        Intent intent = new Intent(this, offline.class);
        startActivity(intent);
    }
}
