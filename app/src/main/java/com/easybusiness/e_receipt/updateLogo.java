package com.easybusiness.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class updateLogo extends AppCompatActivity {
    DatabaseHelper myDb;
    Button update;
    ImageView upload;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    String imageCheck = "notUploaded";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_logo);

        myDb = new DatabaseHelper(this);
        update = findViewById(R.id.update);
        upload = findViewById(R.id.upload);
        mProgressBar = findViewById(R.id.progress_bar);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog nDialog;
                nDialog = new ProgressDialog(updateLogo.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                if(imageCheck == "notUploaded"){
                    Toast.makeText(updateLogo.this, "Attach logo of your shop",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }

                /*
                mStorageRef = FirebaseStorage.getInstance().getReference("shopLogo/").child(login.strUsername);
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(updateLogo.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    if(imageCheck == "notUploaded"){
                        Toast.makeText(updateLogo.this, "Attach logo of your shop",Toast.LENGTH_LONG).show();
                        nDialog.dismiss();
                        return;
                    }
                    uploadFile();
                }*/
                String value = null;
                Cursor res = myDb.getImageInfo(home.preShopEmail);
                if(res != null && res.getCount() > 0){
                    res.moveToFirst();
                    value = res.getString(0);
                }
                if(value != null){
                    upload.buildDrawingCache();
                    Bitmap bitmap = upload.getDrawingCache();
                    byte[] data = getBitmapAsByteArray(bitmap);
                    if(myDb.updateImage(home.preShopEmail, data)){
                        Toast.makeText(updateLogo.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                        //uploadFile();
                        startLogoUpdateActivity();
                    }
                    else{
                        Toast.makeText(updateLogo.this, "server respond error", Toast.LENGTH_SHORT).show();
                        nDialog.dismiss();
                    }
                }
                else{
                    upload.buildDrawingCache();
                    Bitmap bitmap = upload.getDrawingCache();
                    byte[] data = getBitmapAsByteArray(bitmap);
                    if(myDb.insertImage(home.preShopEmail, data)){
                        Toast.makeText(updateLogo.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                        startLogoUpdateActivity();
                        //uploadFile();
                    }
                    else{
                        Toast.makeText(updateLogo.this, "server respond error", Toast.LENGTH_SHORT).show();
                        nDialog.dismiss();
                    }
                }
            }
        });
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(upload);
            imageCheck = "uploaded";
        }
        else{
            imageCheck = "notUploaded";
        }
    }

    private void uploadFile(){
        if(upload != null){
            StorageReference fileReference = mStorageRef.child(login.strUsername + ".jpg");
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },500);

                    Toast.makeText(updateLogo.this, "Upload Successfull",Toast.LENGTH_LONG).show();
                    startLogoUpdateActivity();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(updateLogo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        }
        else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
    public void startLogoUpdateActivity(){
        Intent intent = new Intent(this, home.class);
        Toast.makeText(updateLogo.this, "Shop logo successfully updated",Toast.LENGTH_LONG).show();
        login.videoPlay = "notPlay";
        startActivity(intent);
    }

}