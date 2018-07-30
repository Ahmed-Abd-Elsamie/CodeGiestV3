package com.example.root.codegiest.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.codegiest.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_profile.xml layout file
        setContentView(R.layout.activity_profile);
    }
}
