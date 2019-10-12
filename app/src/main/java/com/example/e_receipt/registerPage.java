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

public class registerPage extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    private FirebaseAuth mAuth;
    EditText uname, pwd, rePwd;
    public static String username, password, rePassword,strUsername;
    public static int count = 100000;
    Button next;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        next = findViewById(R.id.next);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        rePwd = findViewById(R.id.rePassword);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDialog = new ProgressDialog(registerPage.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strUsername = uname.getText().toString();
                password = pwd.getText().toString();
                rePassword = rePwd.getText().toString();
                username =strUsername.replaceAll("[@.]","");

                if(strUsername.isEmpty()){
                    Toast.makeText(registerPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(registerPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(rePassword.isEmpty()){
                    Toast.makeText(registerPage.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(!(strUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+") || strUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"))){
                    Toast.makeText(registerPage.this, "Enter valid email",Toast.LENGTH_LONG).show();
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
                                mAuth.createUserWithEmailAndPassword(strUsername, password)
                                        .addOnCompleteListener(registerPage.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    sendVerificationEmail();
                                                    demoRef1 = demoRef.child(username);
                                                    demoRef1.child("username").setValue(username);
                                                    demoRef1.child("count").setValue(count);

                                                } else {
                                                    startOfflineActivity();
                                                }
                                            }
                                        });
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
        nDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        startLoginActivity();
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(registerPage.this, shopFirstDetail.class));
                    nDialog.dismiss();
                    finish();
                }
                else{
                    Toast.makeText(registerPage.this, "Signup failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
