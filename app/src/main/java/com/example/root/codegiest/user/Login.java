package com.example.root.codegiest.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.root.codegiest.mainScreen.MainTest;
import com.example.root.codegiest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //Initialize the register button
    Button registerBtn ;

    //Initialize the login button to move to the main screen activity
    Button loginBtn ;

    private FirebaseUser user;
    EditText txtPassword;
    EditText txtEmail;
    FirebaseAuth mAuth;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_login.xml layout file
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Assign Views
        txtPassword = (EditText) findViewById(R.id.txt_user_password_login);
        txtEmail = (EditText) findViewById(R.id.txt_user_email_login);
        pb = (ProgressBar) findViewById(R.id.prog_login);



        if (isLogedIn()){

            Intent intent = new Intent(Login.this , MainTest.class);
            startActivity(intent);
            finish();

        }

        //Get the required button
        registerBtn = (Button) findViewById(R.id.registerButton);

        //Select the button to move to the next activity
        loginBtn = (Button) findViewById(R.id.login_button);




        //Setup the register button when it clicked
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go to the next Register Activity
                Intent intent = new Intent(Login.this , Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right , R.anim.slide_out_right);
                finish();
            }
        });


        //Setup the login button to move to the next activity when the button is clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String email = txtEmail.getText().toString().trim();
                final String pass = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed Please Check your Data!", Toast.LENGTH_SHORT).show();
                        }else{

                            pb.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(Login.this , MainTest.class));
                            finish();

                        }
                    }
                });



            }
        });

    }


    private boolean isLogedIn(){

        if (user != null){
            return true;
        }else {
            return false;
        }

    }

}
