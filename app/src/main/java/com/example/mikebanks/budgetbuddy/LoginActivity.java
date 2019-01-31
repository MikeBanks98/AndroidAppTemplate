package com.example.mikebanks.budgetbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == btnSignIn.getId()) {
                signIn(txtEmail.getText().toString(), txtPassword.getText().toString());
            } else if (view.getId() == btnCreateAccount.getId()) {
                //TODO: Temp
                createAccount(txtEmail.getText().toString(), txtPassword.getText().toString());
                //TODO: Navigate to Create Account Activity
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        setupViews();
    }

    @Override
    public void onStart() {
        super.onStart();

        //check if user is signed in (non-null) and update UI accordingly
        //FirebaseUser currentUser = auth.getCurrentUser();

        //TODO: Update UI
    }

    private void setupViews() {
        txtEmail = findViewById(R.id.edt_email);
        txtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_signIn);
        btnCreateAccount = findViewById(R.id.btn_createAccount);

        btnSignIn.setOnClickListener(buttonClickListener);
        btnCreateAccount.setOnClickListener(buttonClickListener);
    }

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Authentication passed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Log.w("FIREBASE", "createUserWithEmail:failure", task.getException());

                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Create Account - Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //TODO: Auth failed - Do not create Account
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Signin - Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //TODO: Auth failed - Do not sign in
                        }
                    }
                });
    }

}
