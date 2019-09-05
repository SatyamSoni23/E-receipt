package com.example.e_receipt;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
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

public class shopLogoUpload extends AppCompatActivity {

    Button next;
    ImageView upload;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_logo_upload);

        next = findViewById(R.id.next);
        upload = findViewById(R.id.upload);
        mProgressBar = findViewById(R.id.progress_bar);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStorageRef = FirebaseStorage.getInstance().getReference("shopLogo/").child(registerPage.username);
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(shopLogoUpload.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    if(upload.getDrawable() == null){
                        Toast.makeText(shopLogoUpload.this, "Attach logo of your shop",Toast.LENGTH_LONG).show();
                        return;
                    }
                    uploadFile();
                }

            }
        });
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
        }
    }

    private void uploadFile(){
        if(upload != null){
            StorageReference fileReference = mStorageRef.child(registerPage.username + ".jpg");
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

                    Toast.makeText(shopLogoUpload.this, "Upload Successfull",Toast.LENGTH_LONG).show();
                    startNextActivity();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(shopLogoUpload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void startNextActivity(){
        Intent intent = new Intent(this, owerDetail.class);
        startActivity(intent);
    }
}
