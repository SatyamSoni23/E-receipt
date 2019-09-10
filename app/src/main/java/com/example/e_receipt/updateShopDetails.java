package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class updateShopDetails extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    Button update;
    EditText shopName, shopMobile, shopAddress, shopPincode, shopEmail, gstNumber, slogan;
    public static String strShopName, strShopMobile, strShopAddress, strShopPincode, strShopEmail, strGstNumber, strSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop_details);

        update = findViewById(R.id.update);
        shopName = findViewById(R.id.shopName);
        shopMobile = findViewById(R.id.shopMobile);
        shopAddress = findViewById(R.id.shopAddress);
        shopPincode = findViewById(R.id.shopPincode);
        shopEmail = findViewById(R.id.shopEmail);
        gstNumber = findViewById(R.id.gstNumber);
        slogan = findViewById(R.id.slogan);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("shopDetail").exists()){
                    if(dataSnapshot.child("shopDetail").child("shopName").exists()){
                        shopName.setText(dataSnapshot.child("shopDetail").child("shopName").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("shopMobile").exists()){
                        shopMobile.setText(dataSnapshot.child("shopDetail").child("shopMobile").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("shopAddress").exists()){
                        shopAddress.setText(dataSnapshot.child("shopDetail").child("shopAddress").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("shopPincode").exists()){
                        shopPincode.setText(dataSnapshot.child("shopDetail").child("shopPincode").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("shopEmail").exists()){
                        shopEmail.setText(dataSnapshot.child("shopDetail").child("shopEmail").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("gstNumber").exists()){
                        gstNumber.setText(dataSnapshot.child("shopDetail").child("gstNumber").getValue().toString());
                    }
                    if(dataSnapshot.child("shopDetail").child("slogan").exists()){
                        slogan.setText(dataSnapshot.child("shopDetail").child("slogan").getValue().toString());
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
                nDialog = new ProgressDialog(updateShopDetails.this);
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
                    Toast.makeText(updateShopDetails.this, "Enter Shop Name",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopMobile.matches("")){
                    Toast.makeText(updateShopDetails.this, "Enter Mobile No.",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopMobile.length() != 10){
                    Toast.makeText(updateShopDetails.this, "Enter valid mobile number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopAddress.matches("")){
                    Toast.makeText(updateShopDetails.this, "Enter Address",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopPincode.matches("")){
                    Toast.makeText(updateShopDetails.this, "Enter Pincode",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopPincode.length() != 6){
                    Toast.makeText(updateShopDetails.this, "Enter valid pincode number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strGstNumber.matches("")){
                    Toast.makeText(updateShopDetails.this, "Enter GST number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strGstNumber.length() != 15){
                    Toast.makeText(updateShopDetails.this, "Enter valid GST number",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strShopEmail.matches("")){
                    Toast.makeText(updateShopDetails.this, "Enter email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }

                if(!strShopEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(updateShopDetails.this, "Enter valid email",Toast.LENGTH_LONG).show();
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
                startUpdateShopDetailsActivity();

            }
        });
    }
    public void startUpdateShopDetailsActivity(){
        Intent intent = new Intent(this, home.class);
        Toast.makeText(updateShopDetails.this, "Shop details successfully updated",Toast.LENGTH_LONG).show();
        login.videoPlay = "notPlay";
        startActivity(intent);
    }
    public void startWrongActivity(){
        Intent intent = new Intent(this, wrg.class);
        startActivity(intent);
    }
}
