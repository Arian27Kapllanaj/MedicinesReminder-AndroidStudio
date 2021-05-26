package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }
}