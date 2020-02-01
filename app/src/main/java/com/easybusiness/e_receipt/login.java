package com.easybusiness.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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

public class login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    DatabaseHelper myDb;
    Button signin;
    int RC_SIGNIN = 0;
    DatabaseReference rootRef, demoRef,demoRef1;
    private FirebaseAuth mAuth;

    public static String strUsername,strNewUsername, strPassword, videoPlay;
    Button login;
    EditText username, password;
    TextView forgotPassword;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);
        
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Firebase Helper xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDialog = new ProgressDialog(login.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strNewUsername = username.getText().toString();
                strPassword = password.getText().toString();
                strUsername = strNewUsername.replaceAll("[@.]","");
                if(strNewUsername.isEmpty()){
                    Toast.makeText(login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(strPassword.isEmpty()){
                    Toast.makeText(login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(!(strNewUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+") || strNewUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"))){
                    Toast.makeText(login.this, "Enter valid email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }

                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(strUsername).exists())
                        {
                            mAuth.signInWithEmailAndPassword(strNewUsername, strPassword).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {
                                            startActivityLogin();
                                        }
                                    }
                                    else{
                                        startWrongActivity();
                                    }
                                }
                            });
                        }
                        else{
                            startWrongActivity();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startErrorActivity();;
                    }
                });

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgotPasswordActivity();
            }
        });
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
    }

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Firebase Helper xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

    public void startActivityLogin(){
        Intent intent = new Intent(this, home.class);
        videoPlay = "play";
        File myDir = new File(Environment.getExternalStorageDirectory().toString(), "E-Receipt");

        final String newUser, newEmail;
        newUser = strPassword;
        newEmail = strNewUsername;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN", newUser);
        editor.putString("EMAIL", newEmail);
        editor.commit();

        if(myDir.exists()){
            startActivity(intent);
        }
        else{
            myDir.mkdir();
            startActivity(intent);
        }
    }
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

    public void startWrongActivity(){
        Intent intent = new Intent(this, wrg.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startErrorActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startForgotPasswordActivity(){
        Intent intent = new Intent(this, forgotPassword.class);
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

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGNIN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGNIN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(login.this, home.class));
        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code" + e.getStatusCode());
            Toast.makeText(login.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            startActivity(new Intent(login.this, home.class));
        }
        super.onStart();
    }


//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

}
