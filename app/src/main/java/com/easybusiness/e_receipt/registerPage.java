package com.easybusiness.e_receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.google.android.gms.common.SignInButton;
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
    DatabaseHelper myDb;
    Button signin;
    DatabaseReference rootRef, demoRef, demoRef1;
    private FirebaseAuth mAuth;
    EditText uname, pwd, rePwd;
    public static String username, password, rePassword,strUsername, userEmail;
    public static int count = 100000;
    Button next;
    ProgressDialog nDialog;
    public static int flag = 0, authFlag = 0;

    private static final String TAG = "registerPage";
    private Button signInButton;
    private GoogleApiClient.Builder googleApiClient;
    private static final int RC_SIGN_IN = 1;
    String idToken;
    private FirebaseAuth.AuthStateListener authStateListener;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        myDb = new DatabaseHelper(this);
        signInButton = findViewById(R.id.signin);
        next = findViewById(R.id.next);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        rePwd = findViewById(R.id.rePassword);
        mAuth = FirebaseAuth.getInstance();
        nDialog = new ProgressDialog(registerPage.this);
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                flag = 1;
                signIn();
            }
        });

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("E-Receipt");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void startSomethingWentWrongActivity(){
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

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Google Integration xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//

    private void signIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                rootRef = FirebaseDatabase.getInstance().getReference();
                strUsername = account.getEmail();
                username =strUsername.replaceAll("[@.]","");
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username).exists()){
                            Toast.makeText(registerPage.this, "An account with this email id already exists.",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                        else{
                            firebasebaseAuthWithGoogle(account);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startOfflineActivity();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void firebasebaseAuthWithGoogle(GoogleSignInAccount account) {

        final Cursor res = myDb.getAllData(strUsername);

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userEmail = user.getEmail();
                            if(res != null && res.getCount() > 0){
                                login.flag = 1;
                                startActivity(new Intent(registerPage.this, home.class));
                            }
                            else{
                                startActivity(new Intent(registerPage.this, shopFirstDetail.class));
                            }
                            nDialog.dismiss();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(registerPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registerPage.this, somethingWentWrong.class));
                            nDialog.dismiss();
                        }

                        // ...
                    }
                });
    }
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
}
