package com.example.e_receipt;
import android.Manifest;
import android.app.ActionBar;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class transactionDetail extends AppCompatActivity {
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    DatabaseReference rootRef, demoRef, demoRefCount;
    private StorageReference storageRef, dataRef;
    RelativeLayout invoicePage;
    EditText invoiceNo, invoiceDate,
            sNo1, sNo2, sNo3, sNo4, sNo5, sNo6, sNo7, sNo8,
            description1, description2, description3, description4, description5, description6, description7, description8,
            qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8,
            rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8,
            amount1, amount2, amount3, amount4, amount5, amount6, amount7, amount8,
            tax, otherCharges, total, discount, amountCustomer;
    float q1, q2, q3, q4, q5, q6, q7, q8,
        r1, r2, r3, r4, r5, r6, r7, r8,
        t1, t2, t3, t4, t5, t6, t7, t8,
        a1, a2, a3, a4, a5, a6, a7, a8,
        tax1, ext1, grandTotal, disc, totFinal;
    TextView shopName,customerName, customerMobile, customerAddress, shopAddress, shopMobile, shopEmail, gstNumber;
    ImageView shopLogo;
    private Button save;
    String dirpath, strBackground;
    public static int count;
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

        sNo1 = (EditText)findViewById(R.id.sNo1);
        description1 = (EditText)findViewById(R.id.description1);
        qty1 = (EditText)findViewById(R.id.qty1);
        rate1 = (EditText)findViewById(R.id.rate1);
        amount1 = findViewById(R.id.amount1);
        amount1.setEnabled(false);

        sNo2 = (EditText)findViewById(R.id.sNo2);
        description2 = (EditText)findViewById(R.id.description2);
        qty2 = (EditText)findViewById(R.id.qty2);
        rate2 = (EditText)findViewById(R.id.rate2);
        amount2 = findViewById(R.id.amount2);
        amount2.setEnabled(false);

        sNo3 = (EditText)findViewById(R.id.sNo3);
        description3 = (EditText)findViewById(R.id.description3);
        qty3 = (EditText)findViewById(R.id.qty3);
        rate3 = (EditText)findViewById(R.id.rate3);
        amount3 = findViewById(R.id.amount3);
        amount3.setEnabled(false);

        sNo4 = (EditText)findViewById(R.id.sNo4);
        description4 = (EditText)findViewById(R.id.description4);
        qty4 = (EditText)findViewById(R.id.qty4);
        rate4 = (EditText)findViewById(R.id.rate4);
        amount4 = findViewById(R.id.amount4);
        amount4.setEnabled(false);

        sNo5 = (EditText)findViewById(R.id.sNo5);
        description5 = (EditText)findViewById(R.id.description5);
        qty5 = (EditText)findViewById(R.id.qty5);
        rate5 = (EditText)findViewById(R.id.rate5);
        amount5 = findViewById(R.id.amount5);
        amount5.setEnabled(false);

        sNo6 = (EditText)findViewById(R.id.sNo6);
        description6 = (EditText)findViewById(R.id.description6);
        qty6 = (EditText)findViewById(R.id.qty6);
        rate6 = (EditText)findViewById(R.id.rate6);
        amount6 = findViewById(R.id.amount6);
        amount6.setEnabled(false);

        sNo7 = (EditText)findViewById(R.id.sNo7);
        description7 = (EditText)findViewById(R.id.description7);
        qty7 = (EditText)findViewById(R.id.qty7);
        rate7 = (EditText)findViewById(R.id.rate7);
        amount7 = findViewById(R.id.amount7);
        amount7.setEnabled(false);

        sNo8 = (EditText)findViewById(R.id.sNo8);
        description8 = (EditText)findViewById(R.id.description8);
        qty8 = (EditText)findViewById(R.id.qty8);
        rate8 = (EditText)findViewById(R.id.rate8);
        amount8 = findViewById(R.id.amount8);
        amount8.setEnabled(false);

        tax = findViewById(R.id.tax);
        otherCharges = findViewById(R.id.otherCharges);
        total = (EditText)findViewById(R.id.total);
        total.setEnabled(false);
        discount = (EditText)findViewById(R.id.discount);
        amountCustomer = (EditText)findViewById(R.id.amountCustomer);
        amountCustomer.setEnabled(false);
        shopName = (TextView)findViewById(R.id.shopName);
        shopAddress = (TextView)findViewById(R.id.shopAddress);
        shopMobile = (TextView)findViewById(R.id.shopMobile);
        shopEmail = (TextView)findViewById(R.id.shopEmail);
        gstNumber = (TextView)findViewById(R.id.gstNumber);
        shopLogo = (ImageView)findViewById(R.id.shopLogo);
        save = (Button) findViewById(R.id.save);

        strBackground = customerDetail.strRBackground;
        if(strBackground == "Select Receipt Design"){
            invoicePage.setBackgroundResource(R.drawable.invoice);
        }
        else if(strBackground == "Flower"){
            invoicePage.setBackgroundResource(R.drawable.b5);
        }
        else if(strBackground == "Jewel"){
            invoicePage.setBackgroundResource(R.drawable.jewel);
        }
        else if(strBackground == "God"){
            invoicePage.setBackgroundResource(R.drawable.garesh);
        }
        else if(strBackground == "Leaf"){
            invoicePage.setBackgroundResource(R.drawable.leaf);
        }
        else if(strBackground == "Square"){
            invoicePage.setBackgroundResource(R.drawable.b6);
        }
        else if(strBackground == "Yellow"){
            invoicePage.setBackgroundResource(R.drawable.yellow);
        }
        else if(strBackground == "Green Brush"){
            invoicePage.setBackgroundResource(R.drawable.b7);
        }
        else if(strBackground == "Gray Flower"){
            invoicePage.setBackgroundResource(R.drawable.b3);
        }
        else if(strBackground == "Crystal"){
            invoicePage.setBackgroundResource(R.drawable.b1);
        }
        else if(strBackground == "Old paper"){
            invoicePage.setBackgroundResource(R.drawable.b2);
        }
        else if(strBackground == "Ancient Paper"){
            invoicePage.setBackgroundResource(R.drawable.old);
        }
        else{
            invoicePage.setBackgroundResource(R.drawable.invoice);
        }

        customerName.setText(customerDetail.strCustomerName);
        customerMobile.setText(customerDetail.strCustomerMobile);
        customerAddress.setText(customerDetail.strCustomerAddress + " " + customerDetail.strCustomerPincode);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        invoiceDate.setText(date);
        invoiceDate.setEnabled(false);

        storageRef = FirebaseStorage.getInstance().getReference().child("shopLogo").child(login.strUsername);
        dataRef = storageRef.child(login.strUsername + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(transactionDetail.this).load(uri).into(shopLogo);
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRefCount = rootRef.child("E-Receipt").child(login.strUsername).child("count");
        demoRefCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = Integer.valueOf(dataSnapshot.getValue().toString()) + 1;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startErrorActivity();
            }
        });
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

        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate1.getText().toString().isEmpty()){ r1 = 0;}
                else{r1 = Integer.parseInt(rate1.getText().toString());}
                if(qty1.getText().toString().isEmpty()){ q1 = 0;}
                else{q1 = Integer.parseInt(qty1.getText().toString());}
                t1 = r1 * q1;
                amount1.setText(String.valueOf(t1));
            }
        });
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate2.getText().toString().isEmpty()){ r2 = 0;}
                else{r2 = Integer.parseInt(rate2.getText().toString());}
                if(qty2.getText().toString().isEmpty()){ q2 = 0;}
                else{q2 = Integer.parseInt(qty2.getText().toString());}
                t2 = r2 * q2;
                amount2.setText(String.valueOf(t2));
            }
        });
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate3.getText().toString().isEmpty()){ r3 = 0;}
                else{r3 = Integer.parseInt(rate3.getText().toString());}
                if(qty3.getText().toString().isEmpty()){ q3 = 0;}
                else{q3 = Integer.parseInt(qty3.getText().toString());}
                t3 = r3 * q3;
                amount3.setText(String.valueOf(t3));
            }
        });
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate4.getText().toString().isEmpty()){ r4 = 0;}
                else{r4 = Integer.parseInt(rate4.getText().toString());}
                if(qty4.getText().toString().isEmpty()){ q4 = 0;}
                else{q4 = Integer.parseInt(qty4.getText().toString());}
                t4 = r4 * q4;
                amount4.setText(String.valueOf(t4));
            }
        });
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate5.getText().toString().isEmpty()){ r5 = 0;}
                else{r5 = Integer.parseInt(rate5.getText().toString());}
                if(qty5.getText().toString().isEmpty()){ q5 = 0;}
                else{q5 = Integer.parseInt(qty5.getText().toString());}
                t5 = r5 * q5;
                amount5.setText(String.valueOf(t5));
            }
        });
        rate6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate6.getText().toString().isEmpty()){ r6 = 0;}
                else{r6 = Integer.parseInt(rate6.getText().toString());}
                if(qty6.getText().toString().isEmpty()){ q6 = 0;}
                else{q6 = Integer.parseInt(qty6.getText().toString());}
                t6 = r6 * q6;
                amount6.setText(String.valueOf(t6));
            }
        });
        rate7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate7.getText().toString().isEmpty()){ r7 = 0;}
                else{r7 = Integer.parseInt(rate7.getText().toString());}
                if(qty7.getText().toString().isEmpty()){ q7 = 0;}
                else{q7 = Integer.parseInt(qty7.getText().toString());}
                t7 = r7 * q7;
                amount7.setText(String.valueOf(t7));
            }
        });rate8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate8.getText().toString().isEmpty()){ r8 = 0;}
                else{r8 = Integer.parseInt(rate8.getText().toString());}
                if(qty8.getText().toString().isEmpty()){ q8 = 0;}
                else{q8 = Integer.parseInt(qty8.getText().toString());}
                t8 = r8 * q8;
                amount8.setText(String.valueOf(t8));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty1.getText().toString().isEmpty() && qty2.getText().toString().isEmpty()
                        && qty3.getText().toString().isEmpty() && qty4.getText().toString().isEmpty()
                        && qty5.getText().toString().isEmpty() && qty6.getText().toString().isEmpty()
                        && qty7.getText().toString().isEmpty() && qty8.getText().toString().isEmpty()){
                    description1.setError("qty is empty");
                    description1.requestFocus();
                    return;
                }

                if(rate1.getText().toString().isEmpty() && rate2.getText().toString().isEmpty()
                        && rate3.getText().toString().isEmpty() && rate4.getText().toString().isEmpty()
                        && rate5.getText().toString().isEmpty() && rate6.getText().toString().isEmpty()
                        && rate7.getText().toString().isEmpty() && rate8.getText().toString().isEmpty()){
                    description1.setError("rate is empty");
                    description1.requestFocus();
                    return;
                }

                /*
                if(rate1.getText().toString().isEmpty()){ r1 = 0;}
                else{r1 = Integer.parseInt(rate1.getText().toString());}
                if(rate2.getText().toString().isEmpty()){ r2 = 0;}
                else{r2 = Integer.parseInt(rate2.getText().toString());}
                if(rate3.getText().toString().isEmpty()){ r3 = 0;}
                else{r3 = Integer.parseInt(rate3.getText().toString());}
                if(rate4.getText().toString().isEmpty()){ r4 = 0;}
                else{r4 = Integer.parseInt(rate4.getText().toString());}
                if(rate5.getText().toString().isEmpty()){ r5 = 0;}
                else{r5 = Integer.parseInt(rate5.getText().toString());}
                if(rate6.getText().toString().isEmpty()){ r6 = 0;}
                else{r6 = Integer.parseInt(rate6.getText().toString());}
                if(rate7.getText().toString().isEmpty()){ r7 = 0;}
                else{r7 = Integer.parseInt(rate7.getText().toString());}
                if(rate8.getText().toString().isEmpty()){ r8 = 0;}
                else{r8 = Integer.parseInt(rate8.getText().toString());}

                if(qty1.getText().toString().isEmpty()){ q1 = 0;}
                else{q1 = Integer.parseInt(qty1.getText().toString());}
                if(qty2.getText().toString().isEmpty()){ q2 = 0;}
                else{q2 = Integer.parseInt(qty2.getText().toString());}
                if(qty3.getText().toString().isEmpty()){ q3 = 0;}
                else{q3 = Integer.parseInt(qty3.getText().toString());}
                if(qty4.getText().toString().isEmpty()){ q4 = 0;}
                else{q4 = Integer.parseInt(qty4.getText().toString());}
                if(qty5.getText().toString().isEmpty()){ q5 = 0;}
                else{q5 = Integer.parseInt(qty5.getText().toString());}
                if(qty6.getText().toString().isEmpty()){ q6 = 0;}
                else{q6 = Integer.parseInt(qty6.getText().toString());}
                if(qty7.getText().toString().isEmpty()){ q7 = 0;}
                else{q7 = Integer.parseInt(qty7.getText().toString());}
                if(qty8.getText().toString().isEmpty()){ q8 = 0;}
                else{q8 = Integer.parseInt(qty8.getText().toString());}
                */
                if(tax.getText().toString().isEmpty()){ tax1 = 0; }
                else{ tax1 = Integer.parseInt(tax.getText().toString());
                        tax.setText(String.valueOf(tax1));}

                if(otherCharges.getText().toString().isEmpty()){ ext1 = 0;}
                else{ ext1 = Integer.parseInt(otherCharges.getText().toString()); }

                if(discount.getText().toString().isEmpty()){disc = 0;}
                else{disc = Integer.parseInt(discount.getText().toString());}

                if(description1.getText().toString().isEmpty() && description2.getText().toString().isEmpty()
                   && description3.getText().toString().isEmpty() && description4.getText().toString().isEmpty()
                   && description5.getText().toString().isEmpty() && description6.getText().toString().isEmpty()
                   && description7.getText().toString().isEmpty() && description8.getText().toString().isEmpty()){
                    description1.setError("description is empty");
                    description1.requestFocus();
                    return;
                }

                if(discount.getText().toString().isEmpty()){
                    disc = 0;
                }

                t1 = calc(q1, r1);
                t2 = calc(q2, r2);
                t3 = calc(q3, r3);
                t4 = calc(q4, r4);
                t5 = calc(q5, r5);
                t6 = calc(q6, r6);
                t7 = calc(q7, r7);
                t8 = calc(q8, r8);

                if(t1!=0){amount1.setText(String.valueOf(t1));}
                if(t2!=0){amount2.setText(String.valueOf(t2));}
                if(t3!=0){amount3.setText(String.valueOf(t3));}
                if(t4!=0){amount4.setText(String.valueOf(t4));}
                if(t5!=0){amount5.setText(String.valueOf(t5));}
                if(t6!=0){amount6.setText(String.valueOf(t6));}
                if(t7!=0){amount7.setText(String.valueOf(t7));}
                if(t8!=0){amount8.setText(String.valueOf(t8));}



                totFinal = (t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8) + ext1 + ((t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)*tax1)/100;;
                total.setText(String.valueOf(totFinal));
                grandTotal = totFinal - disc;
                amountCustomer.setText(String.valueOf(grandTotal));
                demoRefCount.setValue(count);
                save.setVisibility(View.GONE);
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
    public float calc(float qt, float rt){
        return qt * rt;
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
            PdfWriter.getInstance(document, new FileOutputStream(dirpath +"/E-Receipt" + "/" + count + " - " + customerDetail.strCustomerName + ".pdf")); //  Change pdf's name.
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
        save.setVisibility(View.VISIBLE);
    }
    public void startErrorActivity(){
        Intent intent = new Intent(this, offline.class);
        startActivity(intent);
        save.setVisibility(View.VISIBLE);
    }
}
