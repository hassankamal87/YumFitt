package com.example.yumfit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumfit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button logIn;
    ProgressBar progressBar;
    TextView resetTV;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        initialiseViews();
        mAuth = FirebaseAuth.getInstance();

        logIn.setOnClickListener(v->{
            if(emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")){
                Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
            }else{
                logIn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailEditText.getText());
                String password = String.valueOf(passwordEditText.getText());

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();

                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    logIn.setVisibility(View.VISIBLE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });

            }
        });


        resetTV.setOnClickListener(v->{

        });
    }

    private void initialiseViews(){
        emailEditText = findViewById(R.id.emaileditTextInSignIn);
        passwordEditText = findViewById(R.id.passwordEditTextinSignIn);
        logIn = findViewById(R.id.startBtn);
        resetTV = findViewById(R.id.resetTV);
        resetTV.setPaintFlags(resetTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        progressBar = findViewById(R.id.progressBarInSignIn);
        progressBar.setVisibility(View.GONE);
    }
}