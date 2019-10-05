package com.example.e_receipt;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class home extends AppCompatActivity {
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    DatabaseReference rootRef, demoRef;
    private FirebaseAuth mAuth;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt").child(login.strUsername).child("shopDetail");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("shopEmail").exists()){
                    String preShopEmail = dataSnapshot.child("shopEmail").getValue().toString();
                    mAuth.signInWithEmailAndPassword(preShopEmail, login.strPassword).addOnCompleteListener(home.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    boolean emailVerified = user.isEmailVerified();
                                    if(emailVerified == false){
                                        new AlertDialog.Builder(home.this)
                                                .setTitle("Email Verification")
                                                .setMessage("Please verify your email id")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                }).show();
                                    }
                                }
                            }
                            else{
                                Toast.makeText(home.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        videoView = findViewById(R.id.video);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        if(login.videoPlay == "play"){
            videoView.start();
        }
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.aboutEreceipt){
                    Toast.makeText(home.this, "About E-Receipt", Toast.LENGTH_SHORT).show();
                    startAboutEreceiptActivity();
                }
                else if(id == R.id.updatePassword){
                    Toast.makeText(home.this, "Update Password", Toast.LENGTH_SHORT).show();
                    startUpdatePasswordActivity();
                }
                else if (id == R.id.updateShopDetails){
                    Toast.makeText(home.this, "Update Shop Details", Toast.LENGTH_SHORT).show();
                    startUpdateShopDetailsActivity();
                }
                else if (id == R.id.updateShopLogo){
                    Toast.makeText(home.this, "Update Shop Logo", Toast.LENGTH_SHORT).show();
                    startUpdateShopLogoActivity();
                }
                else if (id == R.id.updateOwnerDetails){
                    Toast.makeText(home.this, "Update Owner Details", Toast.LENGTH_SHORT).show();
                    startUpdateOwnerDetailsActivity();
                }
                else if(id == R.id.contactUs){
                    Toast.makeText(home.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    startContactUsActivity();
                }
                else if(id == R.id.Logout){
                    Toast.makeText(home.this, "Logout", Toast.LENGTH_SHORT).show();
                    startLogoutActivity();
                }
                return false;
            }
        });

        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.invoice){
                    Toast.makeText(home.this, "Make Invoice", Toast.LENGTH_SHORT).show();
                    startInvoiceActivity();
                }
                else if(id == R.id.transferMoney){
                    Toast.makeText(home.this, "Transfer Money", Toast.LENGTH_SHORT).show();
                    startTransferMoneyActivity();
                }
                else if(id == R.id.customerDetails){
                    Toast.makeText(home.this, "Customer Details", Toast.LENGTH_SHORT).show();
                    startCustomerDetailActivity();
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    public void startInvoiceActivity(){
        Intent intent = new Intent(this, customerDetail.class);
        startActivity(intent);
    }
    public void startLogoutActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void startUpdatePasswordActivity(){
        Intent intent = new Intent(this, updatePassword.class);
        startActivity(intent);
    }
    public void startUpdateShopDetailsActivity(){
        Intent intent = new Intent(this, updateShopDetails.class);
        startActivity(intent);
    }
    public void startUpdateShopLogoActivity(){
        Intent intent = new Intent(this, updateLogo.class);
        startActivity(intent);
    }
    public void startUpdateOwnerDetailsActivity(){
        Intent intent = new Intent(this, updateOwnerDetails.class);
        startActivity(intent);
    }
    public void startContactUsActivity(){
        Intent intent = new Intent(this, contactUs.class);
        startActivity(intent);
    }
    public void startAboutEreceiptActivity(){
        Intent intent = new Intent(this, aboutEreceipt.class);
        startActivity(intent);
    }
    public void startCustomerDetailActivity() {

        String dirpath;
        dirpath = android.os.Environment.getExternalStorageDirectory().toString() + "/E-Receipt";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pa = sharedPreferences.getString(dirpath, "");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(pa);
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }
    public void startTransferMoneyActivity(){
        Intent intent = new Intent(this, transferMoney.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(home.this, "Access denied", Toast.LENGTH_SHORT).show();
    }

}

