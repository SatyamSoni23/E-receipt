package com.example.e_receipt;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.util.List;

public class home extends AppCompatActivity {
    public final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        videoView = findViewById(R.id.video);
        //Set MediaController  to enable play, pause, forward, etc options.
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Location of Media File
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
        //Starting VideView By Setting MediaController and URI
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

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
    public void startContactUsActivity(){
        Intent intent = new Intent(this, contactUs.class);
        startActivity(intent);
    }
    public void startAboutEreceiptActivity(){
        Intent intent = new Intent(this, aboutEreceipt.class);
        startActivity(intent);
    }
    public void startCustomerDetailActivity() {
        //String dirpath;
        //dirpath = android.os.Environment.getExternalStorageDirectory().toString();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  File.separator + "Documents" + File.separator);
        intent.setDataAndType(uri, "resource/folder");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }
}
