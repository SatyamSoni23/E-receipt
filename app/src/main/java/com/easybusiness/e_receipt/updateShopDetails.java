package com.easybusiness.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateShopDetails extends AppCompatActivity {
    DatabaseHelper myDb;
    DatabaseReference rootRef, demoRef, demoRef1;
    private FirebaseAuth mAuth;
    ProgressDialog nDialog;
    Button update;
    public static int check;
    EditText shopName, shopMobile, shopAddress, shopPincode, shopEmail, gstNumber, slogan;
    public static String strShopName, strShopMobile, strShopAddress, strShopPincode, strShopEmail, strGstNumber, strSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop_details);

        myDb = new DatabaseHelper(this);

        update = findViewById(R.id.update);
        shopName = findViewById(R.id.shopName);
        shopMobile = findViewById(R.id.shopMobile);
        shopAddress = findViewById(R.id.shopAddress);
        shopPincode = findViewById(R.id.shopPincode);
        shopEmail = findViewById(R.id.shopEmail);
        gstNumber = findViewById(R.id.gstNumber);
        slogan = findViewById(R.id.slogan);

        shopEmail.setText(home.preShopEmail);
        shopEmail.setEnabled(false);

        rootRef = FirebaseDatabase.getInstance().getReference();
        String strNewShopName = null, strNewShopAddress = null, strNewShopMobile = null, strNewShopEmail = null, strNewGstNumber = null, strNewPincode = null, strNewSlogan = null;
        Cursor res = myDb.getAllData(home.preShopEmail);
        if(res != null && res.getCount() > 0){
            res.moveToFirst();
            do{
                strNewShopEmail = res.getString(0);
                shopEmail.setText(strNewShopEmail);
                strNewShopName = res.getString(1);
                shopName.setText(strNewShopName);
                strNewShopMobile = res.getString(2);
                shopMobile.setText(strNewShopMobile);
                strNewShopAddress = res.getString(3);
                shopAddress.setText(strNewShopAddress);
                strNewPincode = res.getString(4);
                shopPincode.setText(strNewPincode);
                strNewGstNumber = res.getString(5);
                gstNumber.setText(strNewGstNumber);
                strNewSlogan = res.getString(6);
                slogan.setText(strNewSlogan);
            }while(res.moveToNext());
        }
        /*demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("shopDetail").exists()){
                    if(dataSnapshot.child("shopDetail").child("shopName").exists()){
                        check = 1;
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
                        strGstNumber = dataSnapshot.child("shopDetail").child("gstNumber").getValue().toString();
                        if(strGstNumber == "None"){
                            gstNumber.setText("");
                        }
                    }
                    if(dataSnapshot.child("shopDetail").child("slogan").exists()){
                        strSlogan = dataSnapshot.child("shopDetail").child("slogan").getValue().toString();
                        if(strSlogan == "None"){
                            slogan.setText("");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startWrongActivity();
            }
        });
         */

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        //demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    strGstNumber = "None";
                }
                if(strGstNumber.length() != 15){
                    if(strGstNumber != "None") {
                        Toast.makeText(updateShopDetails.this, "Enter valid GST number",Toast.LENGTH_LONG).show();
                        nDialog.dismiss();
                        return;
                    }
                }
                /*
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
                 */
                Cursor res = myDb.getInfo();
                if(res != null){
                    if(myDb.updateData(strShopName, strShopMobile, strShopAddress, strShopPincode, strShopEmail, strGstNumber, strSlogan)){
                        Toast.makeText(updateShopDetails.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(updateShopDetails.this, "Error in data insertion", Toast.LENGTH_SHORT).show();
                        startSmwActivity();
                    }
                }
                else{
                    if(myDb.insertData(strShopName, strShopMobile, strShopAddress, strShopPincode, strShopEmail, strGstNumber, strSlogan)){
                        Toast.makeText(updateShopDetails.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(updateShopDetails.this, "Error in data insertion", Toast.LENGTH_SHORT).show();
                        startSmwActivity();
                    }
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
        nDialog.dismiss();
    }
    public void startWrongActivity(){
        Intent intent = new Intent(this, wrg.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startOfflineActivity(){
        Intent intent = new Intent(this, offline.class);
        startActivity(intent);
    }
    public void startSmwActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        startActivity(intent);
    }
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(updateShopDetails.this, home.class));
                    login.videoPlay = "notPlay";
                    nDialog.dismiss();
                    finish();
                }
                else{
                    Toast.makeText(updateShopDetails.this, "signup Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
