package com.easybusiness.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerPage extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    Button signin;
    int RC_SIGNIN = 0;
    DatabaseReference rootRef, demoRef, demoRef1;
    private FirebaseAuth mAuth;
    EditText uname, pwd, rePwd;
    public static GoogleSignInAccount account;
    public static String username, password, rePassword,strUsername, userEmail;
    public static int count = 100000;
    Button next;
    ProgressDialog nDialog;

    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        signin = findViewById(R.id.signin);
        next = findViewById(R.id.next);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        rePwd = findViewById(R.id.rePassword);
        mAuth = FirebaseAuth.getInstance();

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(registerPage.this, shopFirstDetail.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(registerPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//


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
                if(password.isEmpty() || password.length() < 6){
                    Toast.makeText(registerPage.this, "Password length should be greater then 6", Toast.LENGTH_SHORT).show();
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

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Firebase Helper xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

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
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

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

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Firebase Helper xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
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
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
    /*
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGNIN);
    }*/
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGNIN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGNIN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){

                account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            }else{
                Toast.makeText(registerPage.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userEmail = user.getEmail();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(registerPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGNIN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(registerPage.this, shopFirstDetail.class));

        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code" + e.getStatusCode());
            Toast.makeText(registerPage.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    /*
    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            startActivity(new Intent(registerPage.this, shopFirstDetail.class));
        }
        super.onStart();
    }

     */
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

}
