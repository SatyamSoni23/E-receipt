package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateOwnerDetails extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    EditText ownerName, ownerMobile, ownerAddress, ownerPincode, ownerEmail;
    Button update;
    public static String strOwnerName, strOwnerMobile, strOwnerAddress, strOwnerPincode, strOwnerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner_details);

        update = findViewById(R.id.update);
        ownerName = findViewById(R.id.ownerName);
        ownerMobile = findViewById(R.id.ownerMobile);
        ownerAddress = findViewById(R.id.ownerAddress);
        ownerPincode = findViewById(R.id.onwerPincode);
        ownerEmail = findViewById(R.id.ownerEmail);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("ownerDetail").exists()){
                    if(dataSnapshot.child("ownerDetail").child("ownerName").exists()){
                        ownerName.setText(dataSnapshot.child("ownerDetail").child("ownerName").getValue().toString());
                    }
                    if(dataSnapshot.child("ownerDetail").child("ownerMobile").exists()){
                        ownerMobile.setText(dataSnapshot.child("ownerDetail").child("ownerMobile").getValue().toString());
                    }
                    if(dataSnapshot.child("ownerDetail").child("ownerAddress").exists()){
                        ownerAddress.setText(dataSnapshot.child("ownerDetail").child("ownerAddress").getValue().toString());
                    }
                    if(dataSnapshot.child("ownerDetail").child("ownerPincode").exists()){
                        ownerPincode.setText(dataSnapshot.child("ownerDetail").child("ownerPincode").getValue().toString());
                    }
                    if(dataSnapshot.child("ownerDetail").child("ownerEmail").exists()){
                        ownerEmail.setText(dataSnapshot.child("ownerDetail").child("ownerEmail").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startWrongActivity();
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog nDialog;
                nDialog = new ProgressDialog(updateOwnerDetails.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strOwnerName = ownerName.getText().toString();
                strOwnerMobile = ownerMobile.getText().toString();
                strOwnerAddress = ownerAddress.getText().toString();
                strOwnerPincode = ownerPincode.getText().toString();
                strOwnerEmail = ownerEmail.getText().toString();

                if(strOwnerName.matches("")){
                    Toast.makeText(updateOwnerDetails.this, "Enter Shop Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!strOwnerName.matches("[a-zA-Z\\s]*")){
                    Toast.makeText(updateOwnerDetails.this, "Enter valid Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.matches("")){
                    Toast.makeText(updateOwnerDetails.this, "Enter Mobile No.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerMobile.length() != 10){
                    Toast.makeText(updateOwnerDetails.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerAddress.matches("")){
                    Toast.makeText(updateOwnerDetails.this, "Enter enter Address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.matches("")){
                    Toast.makeText(updateOwnerDetails.this, "Enter Pincode",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerPincode.length() != 6){
                    Toast.makeText(updateOwnerDetails.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(strOwnerEmail.matches("")){
                    Toast.makeText(updateOwnerDetails.this, "Enter Email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!strOwnerEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(updateOwnerDetails.this, "Enter valid Email",Toast.LENGTH_LONG).show();
                    return;
                }
                demoRef1 = demoRef.child("ownerDetail");
                demoRef1.child("ownerName").setValue(strOwnerAddress);
                demoRef1.child("ownerMobile").setValue(strOwnerMobile);
                demoRef1.child("ownerAddress").setValue(strOwnerAddress);
                demoRef1.child("ownerPincode").setValue(strOwnerPincode);
                demoRef1.child("ownerEmail").setValue(strOwnerEmail);
                startUpdateOwnerDetailsActivity();

            }
        });
    }

    public void startUpdateOwnerDetailsActivity(){
        Intent intent = new Intent(this, home.class);
        Toast.makeText(updateOwnerDetails.this, "Owner details successfully updated",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    public void startWrongActivity(){
        Intent intent = new Intent(this, wrg.class);
        startActivity(intent);
    }
}

