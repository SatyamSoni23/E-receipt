package com.example.e_receipt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class transferMoney extends AppCompatActivity {

    EditText amountEt, noteEt, nameEt, upiIdEt;
    Button send;

    final int UPI_PAYMENT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);

        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi_id);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upi_Id = upiIdEt.getText().toString();
                payUsingUpi(amount, note, name, upi_Id);
            }
        });
    }
    void payUsingUpi(String amount, String upiId, String name, String note){
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
        Intent upipayIntent = new Intent(Intent.ACTION_VIEW);
        upipayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upipayIntent, "Pay with");
        if(null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser, UPI_PAYMENT);
        }
        else{
            Toast.makeText(transferMoney.this, "No upi app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }
}
