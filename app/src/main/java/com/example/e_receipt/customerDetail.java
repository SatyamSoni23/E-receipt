package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class customerDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    Button next;
    EditText customerName, customerMobile, customerAddress, customerPincode, customerEmail, otherDetail;
    Spinner rBackground;
    public static String strRBackground, strCustomerName, strCustomerMobile, strCustomerAddress, strCustomerPincode, strCustomerEmail, strOtherDetail;
    String detailsCheck = "Incorrect";
    ProgressDialog nDialog;
    public static List<String> list = new ArrayList<>();
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
        rBackground = findViewById(R.id.rBackground);
        next = findViewById(R.id.next);

        rBackground.setSelection(0);
        list.add("Select Receipt Design");
        list.add("Flower");
        list.add("Jewel");
        list.add("God");
        list.add("Leaf");
        list.add("Square");
        list.add("Yellow");
        list.add("Green Brush");
        list.add("Gray Flower");
        list.add("Crystal");
        list.add("Old paper");
        list.add("Ancient Paper");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rBackground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                rBackground.setSelection(i);
                strRBackground = list.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strRBackground = "Select Receipt Design";

            }
        });
        rBackground.setAdapter(arrayAdapter);
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDialog = new ProgressDialog(customerDetail.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("shopDetail").exists()){
                            detailsCheck = "correct";
                        }
                        else{
                            Toast.makeText(customerDetail.this, "Please update your shop details",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                            return;
                        }
                        detailsCheck = "Incorrect";
                        if(dataSnapshot.child("ownerDetail").exists()){
                            detailsCheck = "correct";
                        }
                        else {
                            Toast.makeText(customerDetail.this, "Please update owner details",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
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
                                nDialog.dismiss();
                                return;
                            }
                            if(!strCustomerName.matches("[a-zA-Z\\s]*")){
                                Toast.makeText(customerDetail.this, "Enter valid name",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }
                            if(strCustomerMobile.matches("")){
                                Toast.makeText(customerDetail.this, "Enter mobile number",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }
                            if(strCustomerMobile.length() != 10){
                                Toast.makeText(customerDetail.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }
                            if(strCustomerAddress.matches("")){
                                Toast.makeText(customerDetail.this, "Enter address",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }
                            if(strCustomerPincode.matches("")){
                                Toast.makeText(customerDetail.this, "Enter pincode",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(strCustomerPincode.length() != 6){
                                Toast.makeText(customerDetail.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }
                            if(strCustomerEmail.matches("")){
                                Toast.makeText(customerDetail.this, "Enter email",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(!(strCustomerEmail.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+") || strCustomerEmail.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"))){
                                Toast.makeText(customerDetail.this, "Enter valid email",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                                return;
                            }

                            startNextActivity();
                        }
                        else{
                            nDialog.dismiss();
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
        list.clear();
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startSomethingWentWrongActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        list.clear();
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startHomeActivity(){
        Intent intent = new Intent(this, home.class);
        list.clear();
        startActivity(intent);
        nDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        startHomeActivity();
        login.videoPlay = "notPlay";
    }
}
