package com.example.proyectoindividualapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityProhibida extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(ActivityProhibida.this, MainActivity.class));
        } else {
            startActivity(new Intent(ActivityProhibida.this, LoginActivity.class));
        }

    }
}