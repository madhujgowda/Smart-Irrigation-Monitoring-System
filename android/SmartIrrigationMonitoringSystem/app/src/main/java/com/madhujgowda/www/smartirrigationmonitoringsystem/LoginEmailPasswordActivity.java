package com.madhujgowda.www.smartirrigationmonitoringsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmailPasswordActivity extends AppCompatActivity {

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    //Declare Views
    EditText emailEditTextView;
    EditText passwordEditTextView;

    //Declare ProgressDialog
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LoginEmailPasswordActivity.this);

        emailEditTextView = (EditText) findViewById(R.id.emailEditText);
        passwordEditTextView = (EditText)findViewById(R.id.passwordEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check to see if the user is currently signed in.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user)
    {
        progressDialog.hide();
        if (user!= null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view)
    {
        if (!validateForm())
        {
            return;
        }

        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        //Sign in existing users
        mAuth.signInWithEmailAndPassword(emailEditTextView.getText().toString(),passwordEditTextView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginEmailPasswordActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            Toast.makeText(LoginEmailPasswordActivity.this, "Email / Password incorrect", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public boolean validateForm()
    {
        String email = emailEditTextView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_LONG).show();
            emailEditTextView.setError("Required");
            return false;
        }

        String password = passwordEditTextView.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Password field is empty",Toast.LENGTH_LONG).show();
            passwordEditTextView.setError("Required");
            return false;
        }
        return true;
    }

}
