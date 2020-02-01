package com.easybusiness.e_receipt;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class transactionDetail extends AppCompatActivity {
    DatabaseHelper myDb;
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    DatabaseReference rootRef, demoRef, demoRefCount;
    private StorageReference storageRef, dataRef;
    String date;
    RelativeLayout invoicePage;
    EditText invoiceNo, invoiceDate,
            sNo1, sNo2, sNo3, sNo4, sNo5, sNo6, sNo7, sNo8,
            description1, description2, description3, description4, description5, description6, description7, description8,
            qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8,
            rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8,
            amount1, amount2, amount3, amount4, amount5, amount6, amount7, amount8,
            tax, otherCharges, total, discount, amountCustomer;
    float t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0,
        tax1, ext1, grandTotal, disc, totFinal = 0;
    TextView shopName,customerName, customerMobile, customerAddress, shopAddress, shopMobile, shopEmail, gstNumber;
    ImageView shopLogo;
    private Button save;
    String dirpath, strBackground;
    public static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        myDb = new DatabaseHelper(this);
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

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        invoiceDate.setText(date);
        invoiceDate.setEnabled(false);

/*
        storageRef = FirebaseStorage.getInstance().getReference().child("shopLogo").child(login.strUsername);
        dataRef = storageRef.child(login.strUsername + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(transactionDetail.this).load(uri).into(shopLogo);
            }
        });*/
        byte data[] = new byte[0];
        Cursor res1 = myDb.getImageInfo();
        if(res1 != null && res1.getCount() > 0){
            res1.moveToFirst();
            res1.moveToNext();
            data = res1.getBlob(1);
        }
        Bitmap bitmap = getImage(data);
        shopLogo.setImageBitmap(bitmap);

        rootRef = FirebaseDatabase.getInstance().getReference();
        /*demoRefCount = rootRef.child("E-Receipt").child(login.strUsername).child("count");
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
        */
        String strShopName = null, strShopAddress = null, strShopMobile = null, strShopEmail = null, strGstNumber = null, strPincode = null;
        Cursor res = myDb.getInfo();
        if(res != null && res.getCount() > 0){
            res.moveToFirst();
            do{
                strShopEmail = res.getString(0);
                strShopName = res.getString(1);
                strShopMobile = res.getString(2);
                strShopAddress = res.getString(3);
                strPincode = res.getString(4);
                strGstNumber = res.getString(5);
            }while(res.moveToNext());
        }
        shopName.setText(strShopName);
        shopEmail.setText(strShopEmail);
        shopAddress.setText(strShopAddress + " " + strPincode);
        shopMobile.setText(strShopMobile);
        gstNumber.setText(strGstNumber);

        /*
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
        */
        qty1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount1.setText(calc1());
                t1 = Float.valueOf(amount1.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount1.setText(calc1());
                t1 = Float.valueOf(amount1.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount2.setText(calc2());
                t2 = Float.valueOf(amount2.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount2.setText(calc2());
                t2 = Float.valueOf(amount2.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount3.setText(calc3());
                t3 = Float.valueOf(amount3.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount3.setText(calc3());
                t3 = Float.valueOf(amount3.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount4.setText(calc4());
                t4 = Float.valueOf(amount4.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount4.setText(calc4());
                t4 = Float.valueOf(amount4.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount5.setText(calc5());
                t5 = Float.valueOf(amount5.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount5.setText(calc5());
                t5 = Float.valueOf(amount5.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount6.setText(calc6());
                t6 = Float.valueOf(amount6.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount6.setText(calc6());
                t6 = Float.valueOf(amount6.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount7.setText(calc7());
                t7 = Float.valueOf(amount7.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount7.setText(calc7());
                t7 = Float.valueOf(amount7.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qty8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount8.setText(calc8());
                t8 = Float.valueOf(amount8.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rate8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount8.setText(calc8());
                t8 = Float.valueOf(amount8.getText().toString());
                totFinal = t1+t2+t3+t4+t5+t6+t7+t8;
                total.setText(Float.toString(totFinal));
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otherCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountCustomer.setText(calcFinal());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //demoRefCount.setValue(count);
                save.setVisibility(View.GONE);
                if(layoutToImage()){
                    imageToPDF();
                }
            }
        });
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public boolean layoutToImage() {
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
                    //return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            //return;
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
        return true;
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
            count = 100000;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,customerDetail.class);
        startActivity(intent);
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



    private String calc1() {
        float number1;
        float number2;
        if(qty1.getText().toString() != "" && qty1.getText().length() > 0) {
            number1 = Float.valueOf(qty1.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate1.getText().toString() != "" && rate1.getText().length() > 0) {
            number2 = Float.valueOf(rate1.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc2() {
        float number1;
        float number2;
        if(qty2.getText().toString() != "" && qty2.getText().length() > 0) {
            number1 = Float.valueOf(qty2.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate2.getText().toString() != "" && rate2.getText().length() > 0) {
            number2 = Float.valueOf(rate2.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc3() {
        float number1;
        float number2;
        if(qty3.getText().toString() != "" && qty3.getText().length() > 0) {
            number1 = Float.valueOf(qty3.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate3.getText().toString() != "" && rate3.getText().length() > 0) {
            number2 = Float.valueOf(rate3.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc4() {
        float number1;
        float number2;
        if(qty4.getText().toString() != "" && qty4.getText().length() > 0) {
            number1 = Float.valueOf(qty4.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate4.getText().toString() != "" && rate4.getText().length() > 0) {
            number2 = Float.valueOf(rate4.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc5() {
        float number1;
        float number2;
        if(qty5.getText().toString() != "" && qty5.getText().length() > 0) {
            number1 = Float.valueOf(qty5.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate5.getText().toString() != "" && rate5.getText().length() > 0) {
            number2 = Float.valueOf(rate5.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc6() {
        float number1;
        float number2;
        if(qty6.getText().toString() != "" && qty6.getText().length() > 0) {
            number1 = Float.valueOf(qty6.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate6.getText().toString() != "" && rate6.getText().length() > 0) {
            number2 = Float.valueOf(rate6.getText().toString());
        } else {
            number2 = 0;
        }

        return Float.toString(number1 * number2);
    }
    private String calc7() {
        float number1;
        float number2;
        if(qty7.getText().toString() != "" && qty7.getText().length() > 0) {
            number1 = Float.valueOf(qty7.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate7.getText().toString() != "" && rate7.getText().length() > 0) {
            number2 = Float.valueOf(rate7.getText().toString());
        } else {
            number2 = 0;
        }
        return Float.toString(number1 * number2);
    }
    private String calc8() {
        float number1;
        float number2;
        if(qty8.getText().toString() != "" && qty8.getText().length() > 0) {
            number1 = Float.valueOf(qty8.getText().toString());
        } else {
            number1 = 0;
        }
        if(rate8.getText().toString() != "" && rate8.getText().length() > 0) {
            number2 = Float.valueOf(rate8.getText().toString());
        } else {
            number2 = 0;
        }
        //totFinal = Float.valueOf(total.getText().toString());
        return Float.toString(number1 * number2);
    }
    private String calcFinal() {
        if(total.getText().toString() != "" && total.getText().length() > 0) {
            totFinal = Float.valueOf(total.getText().toString());
        } else {
            totFinal = 0;
        }
        if(otherCharges.getText().toString() != "" && otherCharges.getText().length() > 0) {
            ext1 = Float.valueOf(otherCharges.getText().toString());
        } else {
            ext1 = 0;
        }
        if(tax.getText().toString() != "" && tax.getText().length() > 0) {
            tax1 = Float.valueOf(tax.getText().toString());
        } else {
            tax1 = 0;
        }
        if(discount.getText().toString() != "" && discount.getText().length() > 0) {
            disc = Float.valueOf(discount.getText().toString());
        } else {
            disc = 0;
        }
        return Float.toString(totFinal + ext1 + (totFinal * tax1) / 100 - disc);
    }
}
