package com.example.e_receipt;

import android.content.Intent;
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
