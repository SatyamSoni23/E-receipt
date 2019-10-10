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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updatePassword extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;
    private FirebaseAuth mAuth;

    EditText shopEmail;
    Button update;
    public static String strShopEmail;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        shopEmail = findViewById(R.id.shopEmail);
        update = findViewById(R.id.update);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDialog = new ProgressDialog(updatePassword.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strShopEmail = shopEmail.getText().toString();
                if(login.strNewUsername.equals(strShopEmail)){
                    FirebaseAuth.getInstance().sendPasswordResetEmail(login.strNewUsername)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(updatePassword.this, "Password Reset link send to your email",
                                                Toast.LENGTH_SHORT).show();
                                        startActivityUpdate();
                                    }
                                    else{
                                        startSomethingWentWrongActivity();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(updatePassword.this, "Enter correct shop email",
                            Toast.LENGTH_SHORT).show();
                }
                /*
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        firePassword =dataSnapshot.child("password").getValue().toString();
                        if(firePassword.equals(strOldPassword)){
                            if(strPassword.equals(strRePassword)){
                                demoRef.child("password").setValue(strPassword);
                                Toast.makeText(updatePassword.this, "Password successfully updated",Toast.LENGTH_LONG).show();
                                startActivityNext();
                            }
                            else{
                                Toast.makeText(updatePassword.this, "Password not match",Toast.LENGTH_LONG).show();
                                nDialog.dismiss();
                            }
                        }
                        else{
                            Toast.makeText(updatePassword.this, "Enter correct current password",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startSomethingWentWrongActivity();
                    }
                });

                 */
            }
        });
    }
    public void startActivityUpdate(){
        Intent intent = new Intent(this, home.class);
        login.videoPlay = "notPlay";
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startSomethingWentWrongActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        startActivity(intent);
        nDialog.dismiss();
    }
}
