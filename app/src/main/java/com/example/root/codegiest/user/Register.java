package com.example.root.codegiest.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.codegiest.mainScreen.MainTest;
import com.example.root.codegiest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button registerBtn ;

    EditText txtName;
    EditText txtPassword;
    EditText txtEmail;
    EditText txtRePassword;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Button btnRegister;
    Button btnLoginPage;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_register.xml layout file
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        pd = new ProgressDialog(this);


        // Assign Views

        txtName = (EditText) findViewById(R.id.txt_user_name_register);
        txtPassword = (EditText) findViewById(R.id.txt_user_password_register);
        txtEmail = (EditText) findViewById(R.id.txt_user_email_register);
        txtRePassword = (EditText) findViewById(R.id.txt_user_re_password_register);
        //Get the required button
        registerBtn = (Button) findViewById(R.id.registerButton);


        //Setup the button when it clicked
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Register.this , PhoneAuth.class);
                //startActivity(intent);

                AddNewUser();

            }
        });
    }



    private void AddNewUser(){


        pd.setMessage("Loading...!");


        String name = txtName.getText().toString();
        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPassword.getText().toString().trim();
        String re_pass = txtRePassword.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter your Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(re_pass)) {
            Toast.makeText(getApplicationContext(), "Re Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(re_pass)) {
            Toast.makeText(getApplicationContext(), "Please enter the Same Password", Toast.LENGTH_SHORT).show();
            return;
        }

        pd.show();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd.dismiss();

                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Failed Please Check your Data!", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(email,pass);

                    AddUser();

                    startActivity(new Intent(Register.this, MainTest.class));
                    overridePendingTransition(R.anim.slide_in_right , R.anim.slide_out_right);

                    finish();
                }
            }
        });

    }




    public void AddUser(){


        final String name = txtName.getText().toString();
        final String email = txtEmail.getText().toString().trim();
        final String UID = mAuth.getCurrentUser().getUid().toString();
        databaseReference.child(UID).child("name").setValue(name);
        databaseReference.child(UID).child("email").setValue(email);
        databaseReference.child(UID).child("id").setValue(UID);
        databaseReference.child(UID).child("img").setValue("default");



    }
}
