package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class owerDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    EditText ownerName, ownerMobile, ownerAddress, ownerPincode, ownerEmail;
    TextView tNC;
    Button register;
    CheckBox checkBox;
    ProgressDialog nDialog;
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
        tNC = findViewById(R.id.tNC);

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
                    Toast.makeText(owerDetail.this, "Enter shop name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!strOwnerName.matches("[a-zA-Z\\s]*")){
                    Toast.makeText(owerDetail.this, "Enter valid name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.matches("")){
                    Toast.makeText(owerDetail.this, "Enter mobile no.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.length() != 10){
                    Toast.makeText(owerDetail.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerAddress.matches("")){
                    Toast.makeText(owerDetail.this, "Enter address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.matches("")){
                    Toast.makeText(owerDetail.this, "Enter pincode",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.length() != 6){
                    Toast.makeText(owerDetail.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerEmail.matches("")){
                    Toast.makeText(owerDetail.this, "Enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!strOwnerEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(owerDetail.this, "Enter valid email",Toast.LENGTH_LONG).show();
                    return;
                }
                demoRef1 = demoRef.child("ownerDetail");
                demoRef1.child("ownerName").setValue(strOwnerAddress);
                demoRef1.child("ownerMobile").setValue(strOwnerMobile);
                demoRef1.child("ownerAddress").setValue(strOwnerAddress);
                demoRef1.child("ownerPincode").setValue(strOwnerPincode);
                demoRef1.child("ownerEmail").setValue(strOwnerEmail);

                if(checkBox.isChecked()){
                    nDialog = new ProgressDialog(owerDetail.this);
                    nDialog.setMessage("Loading..");
                    nDialog.setIndeterminate(false);
                    nDialog.setCancelable(true);
                    nDialog.show();
                    startAcivityRegister();
                }
                else{
                    Toast.makeText(owerDetail.this, "Checked the terms and conditions",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                }
            }
        });
        tNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTNCActivity();
            }
        });
    }

    public void startAcivityRegister(){
        Intent intent = new Intent(this, successRegister.class);
        startActivity(intent);
    }
    public void startTNCActivity(){
        Intent intent = new Intent(this, termsAndConditions.class);
        startActivity(intent);
    }
}
