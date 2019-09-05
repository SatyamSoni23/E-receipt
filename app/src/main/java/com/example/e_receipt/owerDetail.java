package com.example.e_receipt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class owerDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    EditText ownerName, ownerMobile, ownerAddress, ownerPincode, ownerEmail;
    Button register;
    CheckBox checkBox;
    public static String strOwnerName, strOwnerMobile, strOwnerAddress, strOwnerPincode, strOwnerEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ower_detail);

        register = findViewById(R.id.register);
        ownerName = findViewById(R.id.ownerName);
        ownerMobile = findViewById(R.id.ownerMobile);
        ownerAddress = findViewById(R.id.ownerAddress);
        ownerPincode = findViewById(R.id.onwerPincode);
        ownerEmail = findViewById(R.id.ownerEmail);
        checkBox = findViewById(R.id.checkbox);

        ownerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ownerName.setHint("");
                    ownerName.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    ownerName.setHint("Owner Name");
                }
            }
        });
        ownerMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ownerMobile.setHint("");
                    ownerMobile.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    ownerMobile.setHint("Mobile No.");
                }
            }
        });
        ownerAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ownerAddress.setHint("");
                    ownerAddress.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    ownerAddress.setHint("Address");
                }
            }
        });
        ownerPincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ownerPincode.setHint("");
                    ownerPincode.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    ownerPincode.setHint("Pincode");
                }
            }
        });
        ownerEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ownerEmail.setHint("");
                    ownerEmail.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    ownerEmail.setHint("Owner Email");
                }
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(registerPage.username);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strOwnerName = ownerName.getText().toString();
                strOwnerMobile = ownerMobile.getText().toString();
                strOwnerAddress = ownerAddress.getText().toString();
                strOwnerPincode = ownerPincode.getText().toString();
                strOwnerEmail = ownerEmail.getText().toString();

                if(strOwnerName.matches("")){
                    Toast.makeText(owerDetail.this, "You did not enter Shop Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!strOwnerName.matches("[a-zA-Z]*")){
                    Toast.makeText(owerDetail.this, "Enter valid Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.matches("")){
                    Toast.makeText(owerDetail.this, "You did not enter Mobile No.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.length() != 10){
                    Toast.makeText(owerDetail.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerAddress.matches("")){
                    Toast.makeText(owerDetail.this, "You did not enter Address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.matches("")){
                    Toast.makeText(owerDetail.this, "You did not enter Pincode",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.length() != 6){
                    Toast.makeText(owerDetail.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerEmail.matches("")){
                    Toast.makeText(owerDetail.this, "You did not enter Email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!strOwnerEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(owerDetail.this, "Enter valid Email",Toast.LENGTH_LONG).show();
                    return;
                }
                demoRef1 = demoRef.child("ownerDetail");
                demoRef1.child("ownerName").setValue(strOwnerAddress);
                demoRef1.child("ownerMobile").setValue(strOwnerMobile);
                demoRef1.child("ownerAddress").setValue(strOwnerAddress);
                demoRef1.child("ownerPincode").setValue(strOwnerPincode);
                demoRef1.child("ownerEmail").setValue(strOwnerEmail);

                if(checkBox.isChecked()){
                    startAcivityRegister();
                }
                else{
                    Toast.makeText(owerDetail.this, "Checked the terms and conditions",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void startAcivityRegister(){
        Intent intent = new Intent(this, successRegister.class);
        startActivity(intent);
    }
}
