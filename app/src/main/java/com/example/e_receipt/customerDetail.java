package com.example.e_receipt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class customerDetail extends AppCompatActivity {

    Button next;
    EditText customerName, customerMobile, customerAddress, customerPincode, customerEmail, otherDetail;
    public static String strCustomerName, strCustomerMobile, strCustomerAddress, strCustomerPincode, strCustomerEmail, strOtherDetail;
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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCustomerName = customerName.getText().toString();
                strCustomerMobile = customerMobile.getText().toString();
                strCustomerAddress = customerAddress.getText().toString();
                strCustomerPincode = customerPincode.getText().toString();
                strCustomerEmail = customerEmail.getText().toString();
                strOtherDetail = otherDetail.getText().toString();
                startNextActivity();
            }
        });
    }
    public void startNextActivity(){
        Intent intent = new Intent(this, transactionDetail.class);
        startActivity(intent);
    }
}
