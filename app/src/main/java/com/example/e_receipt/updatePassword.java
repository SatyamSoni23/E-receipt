package com.example.e_receipt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updatePassword extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;
    EditText password, rePassword;
    Button update;
    public static String strPassword, strRePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        update = findViewById(R.id.update);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    password.setHint("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTypeface(Typeface.create("serif-monospace", Typeface.BOLD_ITALIC));
                }
                else{
                    password.setHint("Password");
                }
            }
        });

        rePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    rePassword.setHint("");
                    rePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    rePassword.setTypeface(Typeface.create("serif-monospace", Typeface.BOLD_ITALIC));
                }
                else{
                    rePassword.setHint("Re-password");
                }
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPassword = password.getText().toString();
                strRePassword = rePassword.getText().toString();
                if(strPassword.equals(strRePassword)){
                    demoRef.child("password").setValue(strPassword);
                    Toast.makeText(updatePassword.this, "Password Successfully Updated",Toast.LENGTH_LONG).show();
                    startActivityNext();
                }
                else{
                    Toast.makeText(updatePassword.this, "Password not match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void startActivityNext(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
}
