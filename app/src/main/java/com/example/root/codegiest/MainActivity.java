package com.example.root.codegiest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.codegiest.user.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {


    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String uid;
    private DatabaseReference reference;



    //The time for splash screen (contain the logo)
    public static int SPLASH_TIME_OUT = 1000 ; // 4000 = 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Setup the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Define the next activity
                Intent loginActivity = new Intent(MainActivity.this , Login.class);
                startActivity(loginActivity);
                finish();
            }
        } , SPLASH_TIME_OUT );  //Set the time for splash screen(4 seconds)


    }




}
