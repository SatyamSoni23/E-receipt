package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shopFirstDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    Button next;
    EditText shopName, shopMobile, shopAddress, shopPincode, shopEmail, gstNumber, slogan;
    public static String strShopName, strShopMobile, strShopAddress, strShopPincode, strShopEmail, strGstNumber, strSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_first_detail);

        next = findViewById(R.id.next);
        shopName = findViewById(R.id.shopName);
        shopMobile = findViewById(R.id.shopMobile);
        shopAddress = findViewById(R.id.shopAddress);
        shopPincode = findViewById(R.id.shopPincode);
        shopEmail = findViewById(R.id.shopEmail);
        gstNumber = findViewById(R.id.gstNumber);
        slogan = findViewById(R.id.slogan);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(registerPage.username);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog nDialog;
                nDialog = new ProgressDialog(shopFirstDetail.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strShopName = shopName.getText().toString();
                strShopMobile = shopMobile.getText().toString();
                strShopAddress = shopAddress.getText().toString();
                strShopPincode = shopPincode.getText().toString();
                strShopEmail = shopEmail.getText().toString();
                strGstNumber = gstNumber.getText().toString();
                strSlogan = slogan.getText().toString();

                if(strShopName.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter shop name",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopMobile.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter mobile no.",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopMobile.length() != 10){
                    Toast.makeText(shopFirstDetail.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopAddress.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter address",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopPincode.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter pincode",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopPincode.length() != 6){
                    Toast.makeText(shopFirstDetail.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strGstNumber.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter GST number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strGstNumber.length() != 15){
                    Toast.makeText(shopFirstDetail.this, "Enter valid GST number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopEmail.matches("")){
                    Toast.makeText(shopFirstDetail.this, "Enter email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }

                if(!strShopEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(shopFirstDetail.this, "Enter valid email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }

                demoRef1 = demoRef.child("shopDetail");
                demoRef1.child("shopName").setValue(strShopName);
                demoRef1.child("shopMobile").setValue(strShopMobile);
                demoRef1.child("shopAddress").setValue(strShopAddress);
                demoRef1.child("shopPincode").setValue(strShopPincode);
                demoRef1.child("gstNumber").setValue(strGstNumber);
                demoRef1.child("shopEmail").setValue(strShopEmail);
                if(slogan.length() == 0){
                    demoRef1.child("slogan").setValue("None");
                }
                else{
                    demoRef1.child("slogan").setValue(strSlogan);
                }

                startNextActivity();
            }
        });
    }
    public void startNextActivity(){
        Intent intent = new Intent(this, shopLogoUpload.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(shopFirstDetail.this, "Please fill the remaining details", Toast.LENGTH_SHORT).show();
    }
}
