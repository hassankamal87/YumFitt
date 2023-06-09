package com.example.yumfit.authentication.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yumfit.R;
import com.example.yumfit.ui.Home2Activity;
import com.example.yumfit.authentication.signin.SignInActivity;
import com.example.yumfit.authentication.signup.SignupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    Button gmailBtn,  signUpEmailBtn;
    TextView logInTV, guestTextView;
    ImageView guestImageView;
    Intent intent;

    private GoogleSignInClient client;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseViews();
        intent = new Intent();


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
        mAuth = FirebaseAuth.getInstance();

        guestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(RegisterActivity.this, Home2Activity.class);
                startActivity(intent);
            }
        });

        gmailBtn.setOnClickListener(v -> {
            client.signOut().addOnCompleteListener(task -> {
                startActivityForResult(client.getSignInIntent(), 9001);
            });
            Intent signInIntent = client.getSignInIntent();
            startActivityForResult(signInIntent, 9001);
        });


        signUpEmailBtn.setOnClickListener(v -> {
            intent.setClass(RegisterActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        logInTV.setOnClickListener(v -> {
            intent.setClass(RegisterActivity.this, SignInActivity.class);
            startActivity(intent);
        });



    }

    private void initialiseViews() {
        gmailBtn = findViewById(R.id.googleBtn);
        signUpEmailBtn = findViewById(R.id.emailBtn);
        logInTV = findViewById(R.id.logInTV);
        logInTV.setPaintFlags(logInTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        guestImageView = findViewById(R.id.guestImageView);
        guestTextView = findViewById(R.id.guestTV);
        guestTextView.setPaintFlags(guestTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("hassankamal", "onActivityResult:  doneeee ");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i("hassankamal", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Log.i("hassankamal", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            intent.setClass(RegisterActivity.this, Home2Activity.class);
                            startActivity(intent);
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("hassankamal", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("hassankamal", "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            intent.setClass(RegisterActivity.this,Home2Activity.class);
            startActivity(intent);
            finish();
        }
    }
}