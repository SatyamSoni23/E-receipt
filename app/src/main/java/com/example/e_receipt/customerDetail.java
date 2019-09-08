package com.example.e_receipt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    Button next;
    EditText customerName, customerMobile, customerAddress, customerPincode, customerEmail, otherDetail;
    public static String strCustomerName, strCustomerMobile, strCustomerAddress, strCustomerPincode, strCustomerEmail, strOtherDetail;
    String detailsCheck = "Incorrect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        customerName = findViewById(R.id.customerName);
        customerMobile = findViewById(R.id.customerMobile);
        customerAddress = findViewById(R.id.customerAddress);
        customerPincode = findViewById(R.id.customerPincode);
        customerEmail = findViewById(R.id.customerEmail);
        otherDetail = findViewById(R.id.otherDetail);
        next = findViewById(R.id.next);
        /*
        customerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    customerName.setHint("");
                    customerName.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    customerName.setHint("Customer Name");
                }
            }
        });
        customerMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    customerMobile.setHint("");
                    customerMobile.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    customerMobile.setHint("Mobile No.");
                }
            }
        });
        customerAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    customerAddress.setHint("");
                    customerAddress.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    customerAddress.setHint("Address");
                }
            }
        });
        customerPincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    customerPincode.setHint("");
                    customerPincode.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    customerPincode.setHint("Pincode");
                }
            }
        });
        customerEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    customerEmail.setHint("");
                    customerEmail.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    customerEmail.setHint("Customer Email");
                }
            }
        });
        otherDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    otherDetail.setHint("");
                    otherDetail.setTypeface(Typeface.create("sans-serif-condensed", Typeface.ITALIC));
                }
                else {
                    otherDetail.setHint("Other Details");
                }
            }
        });

         */
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("shopDetail").exists()){
                            detailsCheck = "correct";
                        }
                        else{
                            Toast.makeText(customerDetail.this, "Please update your shop details",Toast.LENGTH_LONG).show();
                            return;
                        }
                        detailsCheck = "Incorrect";
                        if(dataSnapshot.child("ownerDetail").exists()){
                            detailsCheck = "correct";
                        }
                        else {
                            Toast.makeText(customerDetail.this, "Please update owner details",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(detailsCheck == "correct"){
                            strCustomerName = customerName.getText().toString();
                            strCustomerMobile = customerMobile.getText().toString();
                            strCustomerAddress = customerAddress.getText().toString();
                            strCustomerPincode = customerPincode.getText().toString();
                            strCustomerEmail = customerEmail.getText().toString();
                            strOtherDetail = otherDetail.getText().toString();
                            if(strCustomerName.matches("")){
                                Toast.makeText(customerDetail.this, "Enter customer name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(!strCustomerName.matches("[a-zA-Z\\s]*")){
                                Toast.makeText(customerDetail.this, "Enter valid name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerMobile.matches("")){
                                Toast.makeText(customerDetail.this, "Enter mobile number",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerMobile.length() != 10){
                                Toast.makeText(customerDetail.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerAddress.matches("")){
                                Toast.makeText(customerDetail.this, "Enter address",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerPincode.matches("")){
                                Toast.makeText(customerDetail.this, "Enter pincode",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerPincode.length() != 6){
                                Toast.makeText(customerDetail.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerEmail.matches("")){
                                Toast.makeText(customerDetail.this, "Enter email",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(!strCustomerEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                                Toast.makeText(customerDetail.this, "Enter valid email",Toast.LENGTH_LONG).show();
                                return;
                            }
                            startNextActivity();
                        }
                        else{
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startSomethingWentWrongActivity();
                    }
                });
            }
        });
    }
    public void startNextActivity(){
        Intent intent = new Intent(this, transactionDetail.class);
        startActivity(intent);
    }
    public void startSomethingWentWrongActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        startActivity(intent);
    }
}
