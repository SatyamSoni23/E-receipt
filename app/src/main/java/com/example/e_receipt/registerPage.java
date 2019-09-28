package com.example.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerPage extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    EditText uname, pwd, rePwd;
    public static String username, password, rePassword;
    public static int count = 100000;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        next = findViewById(R.id.next);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        rePwd = findViewById(R.id.rePassword);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog nDialog;
                nDialog = new ProgressDialog(registerPage.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                username = uname.getText().toString();
                password = pwd.getText().toString();
                rePassword = rePwd.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(registerPage.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(registerPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(rePassword.isEmpty()){
                    Toast.makeText(registerPage.this, "Re-Enter Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username).exists()){
                            Toast.makeText(registerPage.this, "Username already registered",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                        else
                        {
                            if(password.equals(rePassword)){
                                demoRef1 = demoRef.child(username);
                                demoRef1.child("username").setValue(username);
                                demoRef1.child("password").setValue(password);
                                demoRef.child("count").setValue(count);
                                startActivityNext();
                            }
                            else{
                                Toast.makeText(registerPage.this, "Password does not match",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startOfflineActivity();
                    }
                });
            }
        });
    }
    public void startActivityNext(){
        Intent intent = new Intent(this, shopFirstDetail.class);
            startActivity(intent);
    }
    public void startOfflineActivity(){
        Intent intent = new Intent(this, offline.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        startLoginActivity();
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
